package platform.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import platform.config.JacksonConfig;
import platform.dto.BodyWithID;
import platform.dto.CodeRequestBody;
import platform.dto.SnippetResult;
import platform.entity.CodeSnippet;
import platform.service.CodeSharingService;
import java.util.*;
import java.util.stream.Stream;

@Controller
public class CodeSharingController {
    @Autowired
    CodeSharingService codeSharingService;
    @Autowired
    JacksonConfig jacksonConfig;

    @GetMapping("/code/{uuid}")
    public String getCode(Model model, @PathVariable UUID uuid) {
        Optional<CodeSnippet> snippet = codeSharingService.getSnippetN(uuid);
        if (snippet.isPresent()) {
            model.addAttribute("title", "Code");
            model.addAttribute("snippets",
                    Stream.of(snippet.get()).toList()
            );
            return "codesnippets";
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found"
            );
        }
    }

    @GetMapping("/code/new")
    public String getNewCode() {
        return "create";
    }

    @GetMapping("/code/latest")
    public String getCode(Model model) {
        model.addAttribute("title", "Latest");
        model.addAttribute("snippets",
                codeSharingService.getTenLastSnippets().stream().toList());
        return "codesnippets";
    }

    @GetMapping("/api/code/latest")
    public ResponseEntity<String> getApiCode() throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();

        headers.add("Content-Type", "application/json");

        return ResponseEntity.ok()
                .headers(headers)
                .body(jacksonConfig.objectMapper().writeValueAsString(
                        codeSharingService.getTenLastSnippets().stream()
                                .map(SnippetResult::new)
                                .toList())
                );
    }

    @GetMapping("/api/code/{uuid}")
    public ResponseEntity<String> getApiCode(@PathVariable UUID uuid) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();

        headers.add("Content-Type", "application/json");

        Optional<CodeSnippet>  result = codeSharingService.getSnippetN(uuid);
        return result.isPresent() ?
                ResponseEntity.ok()
                        .headers(headers)
                        .body(jacksonConfig.objectMapper().writeValueAsString(
                                new SnippetResult(result.get())
                        )) :
                ResponseEntity.notFound().headers(headers).build();
    }

    @PostMapping("/api/code/new")
    public ResponseEntity<String> postNewCode(@RequestBody CodeRequestBody codeRequestBody) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();

        UUID newEntry = codeSharingService.addNewEntry(codeRequestBody.getCode(), codeRequestBody.getTime(), codeRequestBody.getViews());

        headers.add("Content-Type", "application/json");

        return ResponseEntity.ok()
                .headers(headers)
                .body(jacksonConfig.objectMapper().writeValueAsString(new BodyWithID(String.valueOf(newEntry))));
    }
}