<!DOCTYPE html>
<html>
  <head>
    <title>UCSB Buy/Sell</title>
    <#include "head.ftl" />
  </head>
  <body>
    <#include "navbar.ftl" />
    <h1>Admin Post Approval Page</h1>
    <div>
    </div>
    
    <div class="grid-container">
      <#list postings as posting>	
	<div class="grid-item">
	  <h2>${posting.title}</h2>
	  <h3>${posting.email}</h3>
	  <h3>${posting.number}</h3>
	  <h4>${posting.description}</h4>
	  <h3>${posting.id}</h3>	  
	  <form method="get" action="/approve/${posting.id}">	    
	    <input type="submit"  value="Approve">	    	
	  </form>
	  <form method="get" action="/remove/${posting.id}" >
	    <input type="submit" value="Remove">
	  </form>
	  
	</div>
      </#list>
  </body>
</html>
