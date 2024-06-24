<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${title}</title>
    <link rel="stylesheet"
          href="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/styles/default.min.css">
    <script src="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/highlight.min.js"></script>
    <script>hljs.initHighlightingOnLoad();</script>
</head>
<body>
    <#list snippets as snippet>
        <div>
            <span id="load_date">${snippet.date}</span><br>
            <#if snippet.timeLimit != 0 >
                <span id="time_restriction">${snippet.getRemainingTimeInSeconds()}</span><br>
            </#if>
            <#if snippet.viewLimit != 0>
                <span id="views_restriction">${snippet.getRemainingViews()}</span><br>
            </#if>
            <pre id="code_snippet"><code>${snippet.code}</code></pre>
        </div>
    </#list>
</body>
</html>