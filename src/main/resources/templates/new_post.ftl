<!DOCTYPE html>
<html>
 <head>
   <title>New Post</title>
   <#include "head.ftl" />
 </head>
 <body>
   <#include "navbar.ftl" />
   <h1>Make A New Post</h1>
   
   <div class="new-post">
     <form action="new_post" method "get ">
	<p>Title</p><input class="input-field" type="text" name="title">
	<p>Email</p><input class="input-field" type="text" name="email">
	<p>Number</p><input class="input-field" type="text" name="number">
	<p>Description</p><textarea class="input-field"  name="desc" rows="5" cols="33"></textarea>
	<div class="buttons">
	 <input class="button" type="submit" value="Submit">
	 <input class="button" type="reset" value="Reset">
	</div class="buttons">
     </form>
 </body>
</html>

