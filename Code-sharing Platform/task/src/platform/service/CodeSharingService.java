package platform.service;

import org.springframework.stereotype.Service;
import platform.entity.CodeSnippet;
import platform.repositories.SnippetRepository;

import java.time.Instant;
import java.util.*;
import java.util.stream.StreamSupport;

@Service
public class CodeSharingService {

    CodeSharingService(SnippetRepository snippetRepository) {
        this.snippetRepository = snippetRepository;
    }

    private final SnippetRepository snippetRepository;

    public Optional<CodeSnippet> getSnippetN(UUID uuid) {
        Optional<CodeSnippet> snippet = snippetRepository.findById(uuid);
        if (snippet.isPresent()) {
            CodeSnippet s = snippet.get();
            if (s.validate()) {
                s.updateViews();
                 snippetRepository.save(s);
                return Optional.of(s);
            } else {
                snippetRepository.delete(s);
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }
    }

    public UUID addNewEntry(String code, int time, int viewLimit) {
        Date now = Date.from(Instant.now());
        CodeSnippet codeSnippet = new CodeSnippet(code, now, time, viewLimit);
        snippetRepository.save(codeSnippet);
        return codeSnippet.getUuid();
    }

    public List<CodeSnippet> getTenLastSnippets() {
        List<CodeSnippet> out = new ArrayList<>();

        StreamSupport.stream(snippetRepository.findUnrestrictedLatestTop10().spliterator(), false)
                .forEach(s -> {
                    s.updateViews();
                    snippetRepository.save(s);
                    out.add(s);
                });
        return out;
    }
}