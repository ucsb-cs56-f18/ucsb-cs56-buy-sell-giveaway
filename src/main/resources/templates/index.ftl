<!DOCTYPE html>
<html>
  <head>
    <title>UCSB Buy/Sell</title>
    <#include "head.ftl" />
  </head>
  <body>
    <#include "navbar.ftl" />
    <h1>Welcome to UCSB Buy/Sell</h1>
    <p>This webapp would be dedicated to providing a platform on which UCSB students could make post about items they wish to sell and search for items other students are selling. The actual exchanging of items/payment would be handled by the individuals, the webapp just introduces the individuals. </p>
    <div class="grid-container">
      <#list postings as posting>
	<div class="grid-item">
	  <h2>${posting.title}</h2>
	  <h3>${posting.email}</h3>
	  <h3>${posting.number}</h3>
	  <h4>${posting.description}</h4>
	</div>
      </#list>
</table>
</body>
</html>
