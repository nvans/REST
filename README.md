<h2>Application that helps to manage users</h2>

<h2>Frameworks:</h2>
<ul>
<li>Bean management - Spring</li>
<li>Persisting - Hibernate</li>
<li>Testing - Junit</li>
</ul>

<h2>Back-end</h2>
<h4>REST API:</h4>

<h5>Users editing:</h5>
<ul>
<li>/users - (only GET)</li>
<li>/users/new - (only POST)</li>
<li>/users/{id} - (GET, POST, DELETE)</li>
<li>/users/{id}/address - (GET, POST, DELETE)</li>
</ul>

<h5>Groups editing:</h5>
<ul>
<li>/groups - (only GET)</li>
<li>/groups/new - (only POST)</li>
<li>/groups/{id} - (GET, POST)</li>
</ul>

<h5>Search api:</h5>
<ul>
<li>/search/firstname/{firstname}</li>
<li>/search/lastname/{lastname}</li>
<li>/search/email/{email}</li>
<li>/search/birthday/{birthday} - String with format "yyyy-MM-dd" (no validation yet)</li>
</ul>
<h4>Testing</h4>
<ul>
<li>Production DB - (config placed at src/main/resources/META-INF/application.properties)</li>
<li>Test DB - HSQL in-memory (config placed at src/test/resources/application.properties</li>
</ul>

<h2>Front-end:</h2>
Not ready yet.
