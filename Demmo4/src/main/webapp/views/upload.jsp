<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

<script type="text/javascript" src="https://code.jquery.com/jquery.min.js"></script>
<script type="text/javascript" src="http://getbootstrap.com/2.3.2/assets/js/bootstrap.js"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>

<link href="${contextPath}/static/admin_panel/css/bootstrap-datepicker3.min.css" rel="stylesheet" type="text/css" />
<script src="${contextPath}/static/admin_panel/js/bootstrap-datepicker.min.js" type="text/javascript"></script>

<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<c:if test="${status ne Empty}">
	<script>
	alert("<c:out value='${status}'/>");
	</script>
</c:if>
	
	<form action="/upload" method="post" id="save1" enctype="multipart/form-data">
	Upload Document
	<div>
		<span class="colon">:</span> <input type="file" name="dscertificate1"
			id="dscertificate" class="form-control" style="width: 290px"
			onchange="fileValidation()"> <span class="requireed">*</span>
		<span style="color: red; font-size: 12px">(.pdf Only & Maximum
			size 500KB)</span>
	</div>
	<!-- <input type="submit" value="submit" id="sub"> -->
	<button type="button" name="btnSubmit" id="btnSubmit"
											class="btn btn-success" onclick="return showDetais();">
											Show
										</button>
	</form>
	
	<table class="table" style="width: 300px">
 		 <thead>
    		  <tr>
				<th>Sl#</th>
				<th>DocumentPath</th>
				<th>Download</th>
				
				</tr>
			</thead>
			
			<c:forEach items="${mm}" varStatus="ss" var="cc"> 
			<tbody>
    			<tr>
				<td>${ss.count }</td>
				<td>${cc.uploadDocPath}</td>
				<td><a href="/download/${cc.id}">Download</a></td>
				</tr>
			</tbody>
			</c:forEach>
	</table>
	
	
	<script>
	function fileValidation() {
		//alert("adsfdfgfdbsgafdfg")
		var fileInput = document.getElementById('dscertificate');
		//alert("adsfdfgfdbsgafdfg" +fileInput)
		var filePath = fileInput.value;

		// Allowing file type
		var allowedExtensions = /(\.pdf|\.txt|\.png)$/i;
		//var allowedExtensions = /(\.jpg|\.jpeg|\.pdf)$/i;

		if (!allowedExtensions.exec(filePath)) {
			 /* BootstrapAlert('Please select a valid pdf file only',
					'dscertificate');
			fileInput.value = '';  */
			alert('Invalid file type!! Please select a valid pdf file only');
            fileInput.value = '';
			return false;
		} else if (fileInput.files.length > 0) {
			for (const i = 0; i <= fileInput.files.length - 1; i++) {

				const fsize = fileInput.files.item(i).size;
				const file = Math.round((fsize / 1024));
				// The size of the file.
				if (file >= 500) {
					/* BootstrapAlert("File size should not exceed 500 KB",
							'dscertificate');
					fileInput.value = ''; */
					alert('File size should not exceed 500 KB');
	                fileInput.value = '';
					return false;
				}
			}
		} else {

			// Image preview
			if (fileInput.files && fileInput.files[0]) {
				var reader = new FileReader();
				reader.onload = function(e) {
					document.getElementById('dscertificate').innerHTML = '<img src="' + e.target.result
                            + '"/>';
				};

				reader.readAsDataURL(fileInput.files[0]);
			}
		}

	}
</script>

<script type="text/javascript">
 	function showDetais() {
		/*  alert("xsdcfvg");
		var vv = document.getElementById('dscertificate');
		if(vv == ''){
			alert("File shouldnot left black");
			//fileInput.value = '';
			return false;
			}  */
		
		var flag = window.confirm("Are you sure!! You want to Show the Data");
		 if (flag == true) {
			/* $(obj).find("i").show();
			$(obj).attr("disabled", "disabled"); */
			$("#save1").submit();
		}

	} 
 	
	/* $(document).ready(function () {
	    $('#sub').click(function () {
	alert("df");
	var v = $('#dscertificate').val();
	 if (v == '') {
         alert("File shouldn't be blank");
         $('#dscertificate').focus();
         return false;
     }
	  var flag=config('Are you sure');
      if(flag== true){
          alert("Data save Succesffuly");
          return true;
      }else{
          return false;
      }

	    });
	}); */
</script>

</body>
</html>