<%-- 
    Document   : index
    Created on : 8 oct 2022, 22:50:53
    Author     : joshy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
        <title>DES</title>
    </head>
    <body class="bg-dark text-white text-center d-flex align-items-center justify-content-center">
        <div class="container py-5">
            <%if(request.getAttribute("error1")!=null){
                out.println("Error. Solo se permiten archivos con terminación .txt o .des");
            }else if(request.getAttribute("error2")!=null){
                out.println("Error. Digite un archivo y una clave de 8 caracteres");
            }%>
            <form action="main" method="post" enctype="multipart/form-data">
             <div class="row mb-3">
               <label for="Clave" class="col-sm-2 col-form-label" >Clave</label>
               <div class="col-sm-10">
                   <input type="text" name="clave" class="form-control" id="Clave" placeholder="8 caracteres">
               </div>
             </div>
             <div class="row mb-3">
               <label for="file" class="col-sm-2 col-form-label">Archivo</label>
               <div class="col-sm-10">
                   <input type="file" name="archivo" accept=".txt,.des" class="form-control" id="file">
               </div>
             </div>
             <button type="submit" class="btn btn-primary">Enviar</button>
           </form>
        </div>
    </body>
</html>
