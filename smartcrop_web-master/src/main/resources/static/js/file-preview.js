console.log("File Preview page...")
$("#profileDoc, #identityDoc, #resumeDoc, #ppbDoc, #aadharDoc, #bankDetailsDoc").click(function(e) {
    e.preventDefault(); // Prevent default link behavior
    let docType = $('#'+this.id).attr('documentType');
    let docUrl = "/dashboard/" + currentUserType+"/"+currentUserId+"/"+ docType;
    $.ajax({
      url: docUrl,
      method: "GET",
//      xhrFields: {
//        responseType: "blob" // Set the response type to blob
//      },
      success: function(response) {

        if(response.docExtension === "pdf") {
            $("#filePreview").html("<embed  src='data:application/pdf;base64," +  response.document + "' type='application/pdf' width='100%' height='600px'>");
         } else {
             $("#filePreview").html("<img src='data:image/png;base64," + response.document + "', class='img-fluid' alt='image Preview'>");
         }
        // Show the modal
        $("#fileModal").modal("show");
      },
      error: function() {
        alert("Error loading file");
      }
    });
  });

  // Helper function to determine the file type based on its extension
  function getFileType(fileUrl) {
    var extension = fileUrl.substring(fileUrl.lastIndexOf(".") + 1).toLowerCase();

    if (extension === "jpg" || extension === "jpeg" || extension === "png") {
      return "image";
    } else if (extension === "pdf") {
      return "pdf";
    } else {
      return "unknown";
    }
  }