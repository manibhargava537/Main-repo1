var current_slide, next_slide, previous_slide;
var left, opacity, scale;
var animation;

var error = false;

// Title
$("#title").on('change', function () {

if ($("#title option:selected").val()==''){
    $("#error-title").text('Please Select Title.');
        $("#title").addClass("box_error");
        error = true;
}else {
        $("#error-title").text('');
        error = false;
    }
});


// first name
$("#fname").keyup(function() {
    var fname = $("#fname").val();
   
    if (fname == '') {
        $("#error-fname").text('Enter your First name.');
        $("#fname").addClass("box_error");
        error = true;
    } else {
        $("#error-fname").text('');
        error = false;
    }
    if ((fname.length <= 1) || (fname.length > 20)) {
        $("#error-fname").text("User length must be between 2 - 20 Characters.");
        $("#fname").addClass("box_error");
        error = true;
    }
    
    if (!/^[a-zA-Z]*$/g.test(fname)) {
        $("#error-fname").text("Only Characters are allowed.");
        $("#fname").addClass("box_error");
        error = true;
    } else {
        $("#fname").removeClass("box_error");
    }
});

// last name
$("#lname").keyup(function() {
    var lname = $("#lname").val();
    if (lname != lname) {
        $("#error-lname").text('Enter your Last name.');
        $("#lname").addClass("box_error");
        error = true;
    } else {
        $("#error-lname").text('');
        error = false;
    }
    if ((lname.length <= 1) || (lname.length > 20)) {
        $("#error-lname").text("User length must be between 2 - 20 Characters.");
        $("#lname").addClass("box_error");
        error = true;
    }
    if (!/^[a-zA-Z]*$/g.test(lname)) {
        $("#error-lname").text("Only Characters are allowed.");
        $("#lname").addClass("box_error");
        error = true;
    } else {
        $("#lname").removeClass("box_error");
    }
});


// date of birth
$("#bdate").on('change', function () {
var dob = $("#bdate").val();
    
  
    if ( dob == '') {
         $("#error-bdate").text('Enter your Date of Birth.');
        $("#bdate").addClass("box_error");
        error = true;
    }
    else {
        $("#error-bdate").text('');
        error = false;
        $("#bdate").removeClass("box_error");
        
    }
    //  var x = new Date($("#bdate").val());  
    // var Cnow = new Date();  
    // if ($("#bdate").val() == "")   
    // {  
      
    //     $("#bdate").focus();  
    // }   
    // else if (Cnow.getFullYear() - x.getFullYear() < 18)   
    // {  
          
    //     $("#error-bdate").text('You Are Not 18 Year.');
    //     error = true;
         
    // } 

    const d = new Date();
    var e = d.getFullYear();

    var dob = $("#bdate").val();
    var dob2 = dob.substring(0, 4);
    var dob3 = e - dob2;
  
if (dob3 < 18 ) 
{
$("#error-bdate").text('You Are Not 18 Year.');
    
}
else if( dob3 > 90)
{
        $("#error-bdate").text('You Are Above 90 Year.');
   
   
}else{
    $("#error-bdate").text('');
}

   

    });
// Gender

$("#gender").on('change', function () {

if ($("#gender option:selected").val()==''){
    $("#error-gender").text('Please Select Gender.');
        $("#gender").addClass("box_error");
        error = true;
}else {
        $("#error-gender").text('');
        error = false;
    }
});

// email validation
$("#email").keyup(function() {
    // var emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
    var emailReg = /^\w+@(\w)+((\-)\w+)?(\.(\w+)){1,2}$/
    if (!emailReg.test($("#email").val())) {
        $("#error-email").text('Please enter an Email addres.');
        $("#email").addClass("box_error");
        error = true;
    } else {
        $("#error-email").text('');
        error = false;
        $("#email").removeClass("box_error");
    }
});
// Phone
$("#phone").keyup(function() {
    var phone = $("#phone").val();
    if (phone != phone) {
        $("#error-phone").text('Enter your Mobile number.');
        $("#phone").addClass("box_error");
        error = true;
    } else {
        $("#error-phone").text('');
        error = false;
    }
    if (phone.length != 10) {
        $("#error-phone").text("Mobile number Must be 10 digit.");
        $("#phone").addClass("box_error");
        error = true;
    } else {
        $("#phone").removeClass("box_error");
    }
    if(!/^[0-9]+$/.test(phone)) {
          $("#error-phone").text("Mobile number in Digits only.");
        $("#phone").addClass("box_error");
        error = true;
    } else {
        $("#phone").removeClass("box_error");
    }

    
    
});

// Country

$("#country").on('change', function () {

if ($("#country option:selected").val()==''){
    $("#error-country").text('Please Select Country.');
        $("#country").addClass("box_error");
        $('.state').fadeOut();
        error = true;
}else {
        $("#error-country").text('');
        $('.state').fadeIn();
        error = false;
    }
});

// State

$("#state").on('change', function () {

if ($("#state option:selected").val()==''){
    $("#error-state").text('Please Select State.');
        $("#state").addClass("box_error");
        $('.district').fadeOut();
        
        error = true;
}else {
        $("#error-state").text('');
        $('.district').fadeIn();
        error = false;
    }
});

// District

$("#district").on('change', function () {

if ($("#district option:selected").val()==''){



    $("#error-district").text('Please Select District.');
        $("#district").addClass("box_error");
        $('.village').fadeOut();
        error = true;
    }
    else {
        if ($("#country option:selected").val()=='Malaysia'){
        $('.zip').fadeIn();
        $('.village').fadeOut();
         }
         else{
             $("#error-district").text('');
            $('.mandal').fadeIn();
            error = false;

         }
           
        }
    });




// village

$("#village").on('change', function () {

if ($("#village option:selected").val()==''){
    $("#error-village").text('Please Select Village.');
        $("#village").addClass("box_error");
        $('.mandal').fadeOut();
        error = true;
}else {
        $("#error-village").text('');
        $('.mandal').fadeIn();
        error = false;
    }
});

// City

$("#mandal").on('change', function () {

if ($("#mandal option:selected").val()==''){
    $("#error-mandal").text('Please Select City.');
        $("#mandal").addClass("box_error");
        $('.zip').fadeOut();
        error = true;
}else {
        $("#error-mandal").text('');
        $('.village').fadeIn();
        $('.zip').fadeIn();
        error = false;
    }
});

// zip Code



$("#zip").keyup(function() {
    var zip = $("#zip").val();
    if (zip != zip) {
        $("#error-zip").text('Enter your Zip Code.');
        $("#zip").addClass("box_error");
        error = true;
    } else {
        $("#error-zip").text('');
        error = false;
    }
    if (zip.length < 5) {
        $("#error-zip").text("Zip Code more than 5 digit.");
        $("#zip").addClass("box_error");
        error = true;
    } else {
        $("#zip").removeClass("box_error");
    }
    if(!/^[0-9]+$/.test(zip)) {
          $("#error-zip").text("Zip Code in Digits only.");
        $("#zip").addClass("box_error");
        error = true;
    } else {
        $("#zip").removeClass("box_error");
    }

    
    
});

$("#totalLand").keyup(function() {
    var totalLand = $("#totalLand").val();

    if (totalLand == '') {
        $("#error-totalLand").text('Please enter total land in Acres.');
        $("#totalLand").addClass("box_error");
        error = true;
    } else {
        $("#error-totalLand").text('');
        error = false;
    }
});

$("#ppbNo").keyup(function() {
    var totalLand = $("#ppbNo").val();

    if (totalLand == '') {
        $("#error-ppbNo").text('Please enter PPB NO.');
        $("#ppbNo").addClass("box_error");
        error = true;
    } else {
        $("#error-ppbNo").text('');
        error = false;
    }
});

$("#soilType").on('change', function () {

if ($("#soilType option:selected").val()==''){
    $("#error-soilType").text('Please select soil type.');
        $("#soilType").addClass("box_error");
        error = true;
}else {
        $("#error-soilType").text('');
        error = false;
    }
});

$("#currentCrop").on('change', function () {

if ($("#currentCrop option:selected").val()==''){
    $("#error-current-crop").text('Please select current crop.');
        $("#soilType").addClass("box_error");
        error = true;
}else {
        $("#error-current-crop").text('');
        error = false;
    }
});

$("#surveyNos").keyup(function() {
    var totalLand = $("#surveyNos").val();

    if (totalLand == '') {
        $("#error-survey-nos").text('Please enter Survey Nos.');
        $("#surveyNos").addClass("box_error");
        error = true;
    } else {
        $("#error-survey-nos").text('');
        error = false;
    }
});

$("#totalLandHoldingInAcres").keyup(function() {
    var totalLand = $("#totalLandHoldingInAcres").val();

    if (totalLand == '') {
        $("#error-total-land-holding-acres").text('Please enter total land in Acres.');
        $("#totalLandHoldingInAcres").addClass("box_error");
        error = true;
    } else {
        $("#error-total-land-holding-acres").text('');
        error = false;
    }
});

$("#netIncomeFarmer").keyup(function() {
    var totalLand = $("#netIncomeFarmer").val();

    if (totalLand == '') {
        $("#error-net-income-farmer").text('Please enter net Income.');
        $("#netIncomeFarmer").addClass("box_error");
        error = true;
    } else {
        $("#error-net-income-farmer").text('');
        error = false;
    }
});

$("#proposedSurveyNos").keyup(function() {
    var totalLand = $("#proposedSurveyNos").val();

    if (totalLand == '') {
        $("#error-proposed-survey-nos").text('Please enter Survey Nos.');
        $("#proposedSurveyNos").addClass("box_error");
        error = true;
    } else {
        $("#error-proposed-survey-nos").text('');
        error = false;
    }
});

$("#proposedAreaInAcres").keyup(function() {
    var totalLand = $("#proposedAreaInAcres").val();

    if (totalLand == '') {
        $("#error-proposed-acres").text('Please enter proposed area in Acres.');
        $("#proposedAreaInAcres").addClass("box_error");
        error = true;
    } else {
        $("#error-proposed-acres").text('');
        error = false;
    }
});

$("#plantationType").on('change', function () {

if ($("#plantationType option:selected").val()==''){
    $("#error-plantation-type").text('Please select plantation type.');
        $("#plantationType").addClass("box_error");
        error = true;
}else {
        $("#error-plantation-type").text('');
        error = false;
    }
});

$("#waterSource").on('change', function () {

if ($("#waterSource option:selected").val()==''){
    $("#error-water-source").text('Please select current crop.');
        $("#waterSource").addClass("box_error");
        error = true;
}else {
        $("#error-water-source").text('');
        error = false;
    }
});

// register as
$(".custom-radio").click(function() {
 if ($("#register_as:checked").val()=='other'){
$('.other-input').fadeIn();
    } else{
        $('.other-input').fadeOut();
    }
});

// photograph

// $("#image").on('change', function () {
//     var fname1 = $('#image').val()
//     var f1 = $('#image')
// var re = /(\.jpeg)$/i;
// if (!re.exec(fname1)) {
//  $("#error-image").text('Please upload jpeg image.');
//         $("#image").addClass("box_error");
//         error = true;
// }else {
//         $("#error-image").text('');
//         error = false;
//     }

 
// });



// id upload 

// $("#id-card-front").on('change', function () {
//     var fname3 = $('#id-card-front').val()
   
// var re2 = /(\.pdf|\.jpeg|\.png)$/i;
// if (!re2.exec(fname3)) {
   
//   $("#error-id-card-front").text('Please upload  pdf, jpeg or png file.');
//         $("#id-card-front").addClass("box_error");
//         error = true;
// }else {
//         $("#id-card-front").text('');
//         error = false;
       
//     }


// });

// id upload 

// $("#id-card-back").on('change', function () {
//     var fname4 = $('#id-card-back').val()
   
// var re3 = /(\.pdf|\.jpeg|\.png)$/i;
// if (!re3.exec(fname4)) {
   
//   $("#error-id-card-back").text('Please upload  pdf, jpeg or png file.');
//         $("#id-card-back").addClass("box_error");
//         error = true;
// }else {
//         $("#id-card-back").text('');
//         error = false;
       
//     }


// });

// first step validation
$(".fs_next_btn").click(function() {

     // first name
    if ($("#fname").val() == '') {
        $("#error-fname").text('Enter your First name.');
        $("#fname").addClass("box_error");
        error = true;
    } else {
        var fname = $("#fname").val();
        if (fname != fname) {
            $("#error-fname").text('First name is required.');
            error = true;
        } else {
            $("#error-fname").text('');
           
            $("#fname").removeClass("box_error");
        }
        if ((fname.length <= 1) || (fname.length > 20)) {
            $("#error-fname").text("User length must be between 2 - 20 Characters.");
            error = true;
        }
        if (!/^[a-zA-Z]*$/g.test(fname)) {
            $("#error-fname").text("Only Characters are allowed.");
            error = true;
        } else {
            $("#fname").removeClass("box_error");
        }
    }
      // middle name
    if ($("#lname").val() == '') {
        $("#error-lname").text('Enter your Last name.');
        $("#lname").addClass("box_error");
        error = true;
    } else {
        var lname = $("#lname").val();
        if (lname != lname) {
            $("#error-lname").text('Last name is required.');
            error = true;
        } else {
            $("#error-lname").text('');
           
        }
        if ((lname.length <= 1) || (lname.length > 20)) {
            $("#error-lname").text("User length must be between 2 - 20 Characters.");
            error = true;
        } 
        if (!/^[a-zA-Z]*$/g.test(lname)) {
            $("#error-lname").text("Only Characters are allowed.");
            error = true;
        } else {
            $("#lname").removeClass("box_error");
            
        }
    }

// date of birth
    
var dob = $("#bdate").val();

        const d = new Date();
    var e = d.getFullYear();

    var dob = $("#bdate").val();
    var dob2 = dob.substring(0, 4);
    var dob3 = e - dob2;
 
        if (dob3 < 18 ) 
        {
        $("#error-bdate").text('You Are Not 18 Year.');
            
        }
        else if( dob3 > 90)
        {
                $("#error-bdate").text('You Are Above 90 Year.');
           
           
        }else{
            $("#error-bdate").text('');
        }
         if ( dob == '') {
         $("#error-bdate").text('Enter your Date of Birth.');
        $("#bdate").addClass("box_error");
        error = true;
    }
    else {
        $("#error-bdate").text('');
        error = false;
        $("#bdate").removeClass("box_error");
        
    }

    // title
    if ($("#title option:selected").val()==''){
    $("#error-title").text('Please Select Title.');
        $("#title").addClass("box_error");
        error = true;
}else {
        $("#error-title").text('');
        
    }

     var x = new Date($("#bdate").val());  
    var Cnow = new Date();  
    if ($("#bdate").val() == "")   
    {  
        $("#bdate").focus();  
    }   
    else if (Cnow.getFullYear() - x.getFullYear() < 18)   
    {  
          
        $("#error-bdate").text('You Are Not 18 Year.');
        error = true;
         
    }

   
    // Gender


if ($("#gender option:selected").val()==''){
    $("#error-gender").text('Please Select Gender.');
        $("#gender").addClass("box_error");
        error = true;
}else {
        $("#error-gender").text('');
       
    }

    
    
    // animation
    if (!error) {
        if (animation) return false;
        animation = true;

        current_slide = $(this).parent().parent();
        next_slide = $(this).parent().parent().next();

        $("#progress_header li").eq($(".multistep-box").index(next_slide)).addClass("active");

        next_slide.show();
        current_slide.animate({
            opacity: 0
        }, {
            step: function(now, mx) {
                scale = 1 - (1 - now) * 0.2;
                left = (now * 50) + "%";
                opacity = 1 - now;
                current_slide.css({
                    'transform': 'scale(' + scale + ')'
                });
                next_slide.css({
                    'left': left,
                    'opacity': opacity
                });
            },
            duration: 800,
            complete: function() {
                current_slide.hide();
                animation = false;
            },
            easing: 'easeInOutBack'
        });
    }
});
// second step validation
$(".ss_next_btn").click(function() {

    // email
    if ($("#email").val() == '') {
        $("#error-email").text('Please enter an email address.');
        $("#email").addClass("box_error");
        error = true;
    } else {
        var emailReg = /^\w+@(\w)+((\-)\w+)?(\.(\w+)){1,2}$/
        if (!emailReg.test($("#email").val())) {
            $("#error-email").text('Please insert a valid email address.');
            error = true;
        } else {
            $("#error-email").text('');
            $("#email").removeClass("box_error");
        }
    }

     // phone
    if ($("#phone").val() == '') {
        $("#error-phone").text('Enter your Mobile number.');
        $("#phone").addClass("box_error");
        error = true;
    } else {
        var phone = $("#phone").val();
        if (phone != phone) {
            $("#error-phone").text('Mobile number is required.');
            error = true;
        } else {
            $("#error-phone").text('');
            
        }
      if (phone.length != 10) {
        $("#error-phone").text("Mobile number Must be 10 digit.");
        $("#phone").addClass("box_error");
        error = true;
    } else {
        $("#phone").removeClass("box_error");
    }
    if(!/^[0-9]+$/.test(phone)) {
          $("#error-phone").text("Mobile number in Digits only.");
        $("#phone").addClass("box_error");
        error = true;
    } else {
        $("#phone").removeClass("box_error");
    }
    }

   

    if (!error) {
        if (animation) return false;
        animation = true;

        current_slide = $(this).parent().parent();
        next_slide = $(this).parent().parent().next();

        $("#progress_header li").eq($(".multistep-box").index(next_slide)).addClass("active");

        next_slide.show();
        current_slide.animate({
            opacity: 0
        }, {
            step: function(now, mx) {
                scale = 1 - (1 - now) * 0.2;
                left = (now * 50) + "%";
                opacity = 1 - now;
                current_slide.css({
                    'transform': 'scale(' + scale + ')'
                });
                next_slide.css({
                    'left': left,
                    'opacity': opacity
                });
            },
            duration: 800,
            complete: function() {
                current_slide.hide();
                animation = false;
            },
            easing: 'easeInOutBack'
        });
    }

});

// third step validation
$(".ts_next_btn").click(function() {

    // Country
if ($("#country option:selected").val()==''){
    $("#error-country").text('Please Select Country.');
        $("#country").addClass("box_error");
        error = true;
}else {
        $("#error-country").text('');
       
    }


// State


if ($("#state option:selected").val()==''){
    $("#error-state").text('Please Select State.');
        $("#state").addClass("box_error");
        error = true;
}else {
        $("#error-state").text('');
       
    }


// District

if ($("#district option:selected").val()==''){
    $("#error-district").text('Please Select District.');
        $("#district").addClass("box_error");
        error = true;
}else {
        $("#error-district").text('');
        
    }


// City

if ($("#city option:selected").val()==''){
    $("#error-city").text('Please Select City.');
        $("#city").addClass("box_error");
        error = true;
}else {
        $("#error-city").text('');
       
    }


// zip Code

 

var zip = $("#zip").val();
    if (zip != zip) {
        $("#error-zip").text('Enter your Zip Code.');
        $("#zip").addClass("box_error");
        error = true;
    } else {
        $("#error-zip").text('');
        error = false;
    }
    if (zip.length < 5) {
        $("#error-zip").text("Zip Code more than 5 digit.");
        $("#zip").addClass("box_error");
        error = true;
    } else {
        $("#zip").removeClass("box_error");
    }
    if(!/^[0-9]+$/.test(zip)) {
          $("#error-zip").text("Zip Code in Digits only.");
        $("#zip").addClass("box_error");
        error = true;
    } else {
        $("#zip").removeClass("box_error");
    }



   
   

    if (!error) {
        if (animation) return false;
        animation = true;

        current_slide = $(this).parent().parent();
        next_slide = $(this).parent().parent().next();

        $("#progress_header li").eq($(".multistep-box").index(next_slide)).addClass("active");

        next_slide.show();
        current_slide.animate({
            opacity: 0
        }, {
            step: function(now, mx) {
                scale = 1 - (1 - now) * 0.2;
                left = (now * 50) + "%";
                opacity = 1 - now;
                current_slide.css({
                    'transform': 'scale(' + scale + ')'
                });
                next_slide.css({
                    'left': left,
                    'opacity': opacity
                });
            },
            duration: 800,
            complete: function() {
                current_slide.hide();
                animation = false;
            },
            easing: 'easeInOutBack'
        });
    }
});

// Fourth step validation
$(".ts1_next_btn").click(function() {

    // register as
    let empp = $("#register_as:checked").val();

if ($("#register_as:checked").length == 0) {
    $("#error-register_as").text('Please Select One Option.');
        
        error = true;
}else {
        $("#error-register_as").text('');
        error = false;
    }

    
            
 if (!error) {
        if (animation) return false;
        animation = true;
       
        if(empp == 'employee'){
        current_slide = $(this).parent().parent();
        next_slide = $(this).parent().parent().next();
    }else if(empp == 'farmer'){
        current_slide = $(this).parent().parent();
        next_slide = $(this).parent().parent().next().next();

    }else{
        current_slide = $(this).parent().parent();
        next_slide = $(this).parent().parent().next();
    }
        $("#progress_header li").eq($(".multistep-box").index(next_slide)).addClass("active");

        next_slide.show();
        current_slide.animate({
            opacity: 0
        }, {
            step: function(now, mx) {
                scale = 1 - (1 - now) * 0.2;
                left = (now * 50) + "%";
                opacity = 1 - now;
                current_slide.css({
                    'transform': 'scale(' + scale + ')'
                });
                next_slide.css({
                    'left': left,
                    'opacity': opacity
                });
            },
            duration: 800,
            complete: function() {
                current_slide.hide();
                animation = false;
            },
            easing: 'easeInOutBack'
        });
    }
});


// Five step validation
 $(".ss10_next_btn").click(function() {

//id upload 

  var fname4 = $('.emp_doc_photo').val()
  if (fname4 == '') {
    $(".error-emp_doc_photo").text('Please upload  document.');
        $(".emp_doc_photo").addClass("box_error");
        error = true;
    }else {
        $(".error-emp_doc_photo").text('');
      }
       var resume = $('.emp_doc_resume').val()
  if (resume == '') {
    $(".error_emp_doc_resume").text('Please upload  document.');
        $(".emp_doc_resume").addClass("box_error");
        error = true;
    }else {
        $(".error_emp_doc_resume").text('');
      }

     var Nid = $('.emp_doc_id').val()
  if (Nid == '') {
    $(".error_emp_doc_id").text('Please upload  document.');
        $(".emp_doc_id").addClass("box_error");
        error = true;
    }else {
        $(".error_emp_doc_id").text('');
        error = false;
      }



    if (!error) {
        if (animation) return false;
        animation = true;

        current_slide = $(this).parent().parent();
        next_slide = $('.final_slide');



        // $("#progress_header li").eq($(".multistep-box").index(next_slide)).addClass("active");

        next_slide.show();
        current_slide.animate({
            opacity: 0
        }, {
            step: function(now, mx) {
                scale = 1 - (1 - now) * 0.2;
                left = (now * 50) + "%";
                opacity = 1 - now;
                current_slide.css({
                    'transform': 'scale(' + scale + ')'
                });
                next_slide.css({
                    'left': left,
                    'opacity': opacity
                });
            },
            duration: 800,
            complete: function() {
                current_slide.hide();
                animation = false;
            },
            easing: 'easeInOutBack'
        });
    }
});

 // six step validation farmer
 $(".ss1_next_btn_farmer").click(function() {

//id upload 

  var fname4 = $('.fm_doc_photo').val()
  if (fname4 == '') {
    $(".error-fm_doc_photo").text('Please upload  document.');
        $(".fm_doc_photo").addClass("box_error");
        error = true;
    }else {
        $(".error-fm_doc_photo").text('');
      }
    

     var Nid = $('.fm_doc_id').val()
  if (Nid == '') {
    $(".error_fm_doc_id").text('Please upload  document.');
        $(".fm_doc_id").addClass("box_error");
        error = true;
    }else {
        $(".error_fm_doc_id").text('');
        error = false;
      }



    if (!error) {
        if (animation) return false;
        animation = true;

        current_slide = $(this).parent().parent();
        next_slide = $(this).parent().parent().next();



        $("#progress_header li").eq($(".multistep-box").index(next_slide)).addClass("active");

        next_slide.show();
        current_slide.animate({
            opacity: 0
        }, {
            step: function(now, mx) {
                scale = 1 - (1 - now) * 0.2;
                left = (now * 50) + "%";
                opacity = 1 - now;
                current_slide.css({
                    'transform': 'scale(' + scale + ')'
                });
                next_slide.css({
                    'left': left,
                    'opacity': opacity
                });
            },
            duration: 800,
            complete: function() {
                current_slide.hide();
                animation = false;
            },
            easing: 'easeInOutBack'
        });
    }
});

  // seven step validation farmer
 $(".ss2_next_btn_farmer").click(function() {

//id upload 

  var ppb = $('.ppb_no').val()
  if (ppb == '') {
    $(".error_ppb_no").text('Please upload ppb document.');
        $(".ppb_no").addClass("box_error");
        error = true;
    }else {
        $(".error_ppb_no").text('');
      }

       var BankDetail = $('.bank_detail').val()
  if (BankDetail == '') {
    $(".error_bank_detail").text('Please upload bank detail.');
        $(".bank_detail").addClass("box_error");
        error = true;
    }else {
        $(".error_bank_detail").text('');
      }
    

     var Aadhar = $('.aadhar_no').val()
  if (Aadhar == '') {
    $(".error_aadhar_no").text('Please upload aadhar document.');
        $(".aadhar_no").addClass("box_error");
        error = true;
    }else {
        $(".error_aadhar_no").text('');
        error = false;
      }



    if (!error) {
        if (animation) return false;
        animation = true;

        current_slide = $(this).parent().parent();
        next_slide = $(this).parent().parent().next();



        $("#progress_header li").eq($(".multistep-box").index(next_slide)).addClass("active");

        next_slide.show();
        current_slide.animate({
            opacity: 0
        }, {
            step: function(now, mx) {
                scale = 1 - (1 - now) * 0.2;
                left = (now * 50) + "%";
                opacity = 1 - now;
                current_slide.css({
                    'transform': 'scale(' + scale + ')'
                });
                next_slide.css({
                    'left': left,
                    'opacity': opacity
                });
            },
            duration: 800,
            complete: function() {
                current_slide.hide();
                animation = false;
            },
            easing: 'easeInOutBack'
        });
    }
});

 // eight step validation farmer
 $(".ss3_next_btn_farmer").click(function() {

//id upload 

  var survey = $('.survey_no').val()
  if (survey == '') {
    $(".error_survey_no").text('Please enter survey number.');
        $(".survey_no").addClass("box_error");
        error = true;
    }else {
        $(".error_survey_no").text('');
      }

     if ($("#crop_grown option:selected").val()==''){
    $("#error_crop_grown").text('Please Select crop grown.');
        $("#crop_grown").addClass("box_error");
        error = true;
}else {
        $("#error_crop_grown").text('');
       
    }

     var LandHolding = $('.land_holding').val()
  if (LandHolding == '') {
    $(".error_land_holding").text('Please enter total land holding.');
        $(".land_holding").addClass("box_error");
        error = true;
    }else {
        $(".error_land_holding").text('');
      }

       var LandHolding = $('.net_income').val()
  if (LandHolding == '') {
    $(".error_net_income").text('Please enter net income.');
        $(".net_income").addClass("box_error");
        error = true;
    }else {
        $(".error_net_income").text('');
        error = false;
      }



    if (!error) {
        if (animation) return false;
        animation = true;

        current_slide = $(this).parent().parent();
        next_slide = $(this).parent().parent().next();



        $("#progress_header li").eq($(".multistep-box").index(next_slide)).addClass("active");

        next_slide.show();
        current_slide.animate({
            opacity: 0
        }, {
            step: function(now, mx) {
                scale = 1 - (1 - now) * 0.2;
                left = (now * 50) + "%";
                opacity = 1 - now;
                current_slide.css({
                    'transform': 'scale(' + scale + ')'
                });
                next_slide.css({
                    'left': left,
                    'opacity': opacity
                });
            },
            duration: 800,
            complete: function() {
                current_slide.hide();
                animation = false;
            },
            easing: 'easeInOutBack'
        });
    }
});

  // nine step validation farmer
 $(".ss4_next_btn_farmer").click(function() {

//id upload 

  var surveyoil = $('.survey_no_oil').val()
  if (surveyoil == '') {
    $(".error_survey_no_oil").text('Please enter survey number.');
        $(".survey_no_oil").addClass("box_error");
        error = true;
    }else {
        $(".error_survey_no_oil").text('');
      }

      var PerposedArea = $('.perposed_area').val()
  if (PerposedArea == '') {
    $(".error_perposed_area").text('Please enter proposed are.');
        $(".perposed_area").addClass("box_error");
        error = true;
    }else {
        $(".error_perposed_area").text('');
      }


     if ($("#plantation option:selected").val()==''){
    $("#error_plantation").text('Please Select method of plantation.');
        $("#plantation").addClass("box_error");
        error = true;
}else {
        $("#plantation").text('');
       
    }

       var PlantAcer = $('.plant_acer').val()
  if (PlantAcer == '') {
    $(".error_plant_acer").text('Please enter no of plants per acre.');
        $(".plant_acer").addClass("box_error");
        error = true;
    }else {
        $(".error_plant_acer").text('');
      }

        var PlantPalm = $('.palm_plant').val()
  if (PlantPalm == '') {
    $(".error_palm_plant").text('Please enter total oil palm plants.');
        $(".palm_plant").addClass("box_error");
        error = true;
    }else {
        $(".error_palm_plant").text('');
        error = false;
      }

    


    if (!error) {
        if (animation) return false;
        animation = true;

        current_slide = $(this).parent().parent();
        next_slide = $(this).parent().parent().next();



        $("#progress_header li").eq($(".multistep-box").index(next_slide)).addClass("active");

        next_slide.show();
        current_slide.animate({
            opacity: 0
        }, {
            step: function(now, mx) {
                scale = 1 - (1 - now) * 0.2;
                left = (now * 50) + "%";
                opacity = 1 - now;
                current_slide.css({
                    'transform': 'scale(' + scale + ')'
                });
                next_slide.css({
                    'left': left,
                    'opacity': opacity
                });
            },
            duration: 800,
            complete: function() {
                current_slide.hide();
                animation = false;
            },
            easing: 'easeInOutBack'
        });
    }
});


 // ten step validation farmer
 $(".ss5_next_btn_farmer").click(function() {

//id upload 



     if ($("#water_source option:selected").val()==''){
    $("#error_water_source").text('Please Select water source.');
        $("#water_source").addClass("box_error");
        error = true;
}else {
        $("#water_source").text('');
       
    }

       var borewel = $('.boreweel').val();
  if (borewel == '') {
    $(".error_boreweel").text('Please enter borewell wise discharge in LPH.');
        $(".boreweel").addClass("box_error");
        error = true;
    }else {
        $(".error_boreweel").text('');
      }


        var Discharge = $('.discharge').val();
  if (Discharge == '') {
    $(".error_discharge").text('Please enter discharge during summer months.');
        $(".discharge").addClass("box_error");
        error = true;
    }else {
        $(".error_discharge").text('');
       
      }

         var BoreLocation = $('.bore_location').val();
  if (BoreLocation == '') {
    $(".error_bore_location").text('Please enter borewell location.');
        $(".bore_location").addClass("box_error");
        error = true;
    }else {
        $(".error_bore_location").text('');
        error = false;
      }

    


    if (!error) {
        if (animation) return false;
        animation = true;

        current_slide = $(this).parent().parent();
        next_slide = $(this).parent().parent().next();



        $("#progress_header li").eq($(".multistep-box").index(next_slide)).addClass("active");

        next_slide.show();
        current_slide.animate({
            opacity: 0
        }, {
            step: function(now, mx) {
                scale = 1 - (1 - now) * 0.2;
                left = (now * 50) + "%";
                opacity = 1 - now;
                current_slide.css({
                    'transform': 'scale(' + scale + ')'
                });
                next_slide.css({
                    'left': left,
                    'opacity': opacity
                });
            },
            duration: 800,
            complete: function() {
                current_slide.hide();
                animation = false;
            },
            easing: 'easeInOutBack'
        });
    }
});


 // eleven step validation farmer
 $(".ss6_next_btn_farmer").click(function() {

//id upload 



       var BankName = $('.bank_name').val();
  if (BankName == '') {
    $(".error_bank_name").text('Please enter bank name.');
        $(".bank_name").addClass("box_error");
        error = true;
    }else {
        $(".error_bank_name").text('');
      }


        var AccNumber = $('.acc_number').val();
  if (AccNumber == '') {
    $(".error_acc_number").text('Please enter account number.');
        $(".acc_number").addClass("box_error");
        error = true;
    }else {
        $(".error_acc_number").text('');
       
      }


        var BranchName = $('.branch_name').val();
  if (BranchName == '') {
    $(".error_branch_name").text('Please enter branch name.');
        $(".branch_name").addClass("box_error");
        error = true;
    }else {
        $(".error_branch_name").text('');
       
      }

         var IfscCode = $('.ifsc_code').val();
  if (IfscCode == '') {
    $(".error_ifsc_code").text('Please enter ifsc code.');
        $(".ifsc_code").addClass("box_error");
        error = true;
    }else {
        $(".error_ifsc_code").text('');
        error = false;
      }

    


    if (!error) {
        if (animation) return false;
        animation = true;

        current_slide = $(this).parent().parent();
        next_slide = $(this).parent().parent().next();



        $("#progress_header li").eq($(".multistep-box").index(next_slide)).addClass("active");

        next_slide.show();
        current_slide.animate({
            opacity: 0
        }, {
            step: function(now, mx) {
                scale = 1 - (1 - now) * 0.2;
                left = (now * 50) + "%";
                opacity = 1 - now;
                current_slide.css({
                    'transform': 'scale(' + scale + ')'
                });
                next_slide.css({
                    'left': left,
                    'opacity': opacity
                });
            },
            duration: 800,
            complete: function() {
                current_slide.hide();
                animation = false;
            },
            easing: 'easeInOutBack'
        });
    }
});





// previous
$(".previous").click(function() {
    if (animation) return false;
    animation = true;

    current_slide = $(this).parent().parent();
    previous_slide = $(this).parent().parent().prev();

    $("#progress_header li").eq($(".multistep-box").index(current_slide)).removeClass("active");

    previous_slide.show();
    current_slide.animate({
        opacity: 0
    }, {
        step: function(now, mx) {
            scale = 0.8 + (1 - now) * 0.2;
            left = ((1 - now) * 50) + "%";
            opacity = 1 - now;
            current_slide.css({
                'left': left
            });
            previous_slide.css({
                'transform': 'scale(' + scale + ')',
                'opacity': opacity
            });
        },
        duration: 800,
        complete: function() {
            current_slide.hide();
            animation = false;
        },
        easing: 'easeInOutBack'
    });
});




// previous
$(".previous-farmer").click(function() {
    if (animation) return false;
    animation = true;

    current_slide = $(this).parent().parent();
    previous_slide = $('.register_as_slide');

    $("#progress_header li").eq($(".multistep-box").index(current_slide)).removeClass("active");

    previous_slide.show();
    current_slide.animate({
        opacity: 0
    }, {
        step: function(now, mx) {
            scale = 0.8 + (1 - now) * 0.2;
            left = ((1 - now) * 50) + "%";
            opacity = 1 - now;
            current_slide.css({
                'left': left
            });
            previous_slide.css({
                'transform': 'scale(' + scale + ')',
                'opacity': opacity
            });
        },
        duration: 800,
        complete: function() {
            current_slide.hide();
            animation = false;
        },
        easing: 'easeInOutBack'
    });
});

$(".submit_btn").click(function() {
    if (!error){
        $(".main").addClass("form_submited");
    }
    return false;
})


 $(".rowAdder").click(function () {
    debugger
            newRowAdd = '<input type="file" class="form-control m-input">';
 
            $(this).parent('.form-input').find('.newinput').append(newRowAdd);
        });


