package platform.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import platform.entity.CodeSnippet;
import java.util.UUID;

public interface SnippetRepository extends CrudRepository<CodeSnippet, UUID> {
    @Query(value = "SELECT * FROM code_snippet snippet " +
            "WHERE snippet.time_limit <= 0 AND " +
            "snippet.view_limit <= 0" +
            "ORDER BY snippet.date DESC " +
            "LIMIT 10",
            nativeQuery = true
    )
    Iterable<CodeSnippet> findUnrestrictedLatestTop10();
}
