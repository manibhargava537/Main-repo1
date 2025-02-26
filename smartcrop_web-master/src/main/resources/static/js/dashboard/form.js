function showFieldset(fieldsetId) {
    if (validateForm(fieldsetId)) {
        console.log(fieldsetId);
        var currentFieldset = document.getElementById(fieldsetId);
        currentFieldset.style.display = "block";
        var allFieldsets = document.getElementsByTagName("fieldset");
        for (var i = 0; i < allFieldsets.length; i++) {
            if (allFieldsets[i].id !== fieldsetId) {
                allFieldsets[i].style.display = "none";
            }
        }
    }
};



function loadStates() {
    var countryId = $("#country").val();
    $("#state").empty();
    $("#district").empty();
    $("#mandal").empty();
    $("#village").empty();

    if (countryId !== "") {
        $.get("/dropdown/states?countryId=" + countryId, function(data) {
            $("#state").append("<option value=''>Select</option>");
            $.each(data, function(index, state) {
                $("#state").append("<option value='" + state.id + "'+>" + state.name + "</option>")
            });
        });
    }
};

function loadDistricts() {
    var stateId = $("#state").val();
    $("#district").empty();
    $("#mandal").empty();
    $("#village").empty();

    if (stateId !== "") {
        $.get("/dropdown/districts?stateId=" + stateId, function(data) {
            if (data.length === 0) {
                $('.zip').fadeIn();
                //$('.district').fadeOut();
                $('.mandal').fadeOut();
                $('.village').fadeOut();
            }
            $("#district").append("<option value=''>Select</option>");
            $.each(data, function(index, district) {
                $("#district").append("<option value='" + district.id + "'+>" + district.name + "</option>");
            });
        });
    }
};

function loadMandals() {
    var districtId = $("#district").val();
    $("#mandal").empty();
    $("#village").empty();

    if (districtId !== "") {
        $.get("/dropdown/mandals?districtId=" + districtId, function(data) {
            if (data.length === 0) {
                $('.zip').fadeIn();
                $('.mandal').fadeOut();
                $('.village').fadeOut();
            }
            $("#mandal").append("<option value=''>Select</option>");
            $.each(data, function(index, mandal) {
                $("#mandal").append("<option value='" + mandal.id + "'+>" + mandal.name + "</option>")
            });
        });
    }
};

function loadVillages() {
    var mandalId = $("#mandal").val();
    $("#village").empty();

    if (mandalId !== "") {
        $.get("/dropdown/villages?mandalId=" + mandalId, function(data) {
            if (data.length === 0) {
                $('.zip').fadeIn();
                $('.village').fadeOut();
            }
            $("#village").append("<option value=''>Select</option>");
            $.each(data, function(index, village) {
                $("#village").append("<option value='" + village.id + "'+>" + village.name + "</option>")
            });
        });
    }
};

function populateDetails() {
    var plantationType = $("#plantationType").val();
    $("plantsPerAcre").empty();
    $("totalPlants").empty();
    if (plantationType === 'square') {
        $("#plantsPerAcre").val(50);
        var proposedLand = $("#proposedAreaInAcres").val()
        $("#totalPlants").val(Math.ceil(50 * proposedLand));
    }
    if (plantationType === 'triangle') {
        $("#plantsPerAcre").val(57);
        var proposedLand = $("#proposedAreaInAcres").val()
        $("#totalPlants").val(Math.ceil(57 * proposedLand));
    }
};


function validateForm(fieldSetId) {
    var isValid = true;

    if (fieldSetId === '2') {


        if ($("#fname").val() == '') {
            $("#error-fname").text('Enter your First name.');
            $("#fname").addClass("box_error");
            error = true;
            isValid = false;
        } else {
            var fname = $("#fname").val();
            if (fname != fname) {
                $("#error-fname").text('First name is required.');
                error = true;
                isValid = false;
            } else {
                $("#error-fname").text('');

                $("#fname").removeClass("box_error");
            }
            if ((fname.length <= 1) || (fname.length > 20)) {
                $("#error-fname").text("User length must be between 2 - 20 Characters.");
                error = true;
                isValid = false;
            }
            if (!/^[a-zA-Z]*$/g.test(fname)) {
                $("#error-fname").text("Only Characters are allowed.");
                error = true;
                isValid = false;
            } else {
                $("#fname").removeClass("box_error");
            }
        }
        // middle name
        if ($("#lname").val() == '') {
            $("#error-lname").text('Enter your Last name.');
            $("#lname").addClass("box_error");
            error = true;
            isValid = false;
        } else {
            var lname = $("#lname").val();
            if (lname != lname) {
                $("#error-lname").text('Last name is required.');
                error = true;
                isValid = false;
            } else {
                $("#error-lname").text('');

            }
            if ((lname.length <= 1) || (lname.length > 20)) {
                $("#error-lname").text("User length must be between 2 - 20 Characters.");
                error = true;
                isValid = false;
            }
            if (!/^[a-zA-Z]*$/g.test(lname)) {
                $("#error-lname").text("Only Characters are allowed.");
                error = true;
                isValid = false;
            } else {
                $("#lname").removeClass("box_error");

            }
        }

        // care taker
        if ($("#careTaker").val() == '') {
            $("#error-careTaker").text('Enter Care taker name.');
            $("#careTaker").addClass("box_error");
            error = true;
            isValid = false;
        } else {
            var careTaker = $("#careTaker").val();
            if (careTaker != careTaker) {
                $("#error-careTaker").text('careTaker name is required.');
                error = true;
                isValid = false;
            } else {
                $("#error-careTaker").text('');

            }
            if ((lname.length <= 1) || (lname.length > 20)) {
                $("#error-careTaker").text("Name length must be between 2 - 20 Characters.");
                error = true;
                isValid = false;
            }
            if (!/^[a-zA-Z]*$/g.test(lname)) {
                $("#error-careTaker").text("Only Characters are allowed.");
                error = true;
                isValid = false;
            } else {
                $("#careTaker").removeClass("box_error");
            }
        }

        // date of birth

        var dob = $("#bdate").val();

        const d = new Date();
        var e = d.getFullYear();

        var dob = $("#bdate").val();
        var dob2 = dob.substring(0, 4);
        var dob3 = e - dob2;

        if (dob3 < 18) {
            $("#error-bdate").text('You Are Not 18 Year.');

        } else if (dob3 > 90) {
            $("#error-bdate").text('You Are Above 90 Year.');


        } else {
            $("#error-bdate").text('');
        }
        if (dob == '') {
            $("#error-bdate").text('Enter your Date of Birth.');
            $("#bdate").addClass("box_error");
            error = true;
            isValid = false;
        } else {
            $("#error-bdate").text('');
            error = false;
            $("#bdate").removeClass("box_error");

        }

        // title
        if ($("#title option:selected").val() == '') {
            $("#error-title").text('Please Select Title.');
            $("#title").addClass("box_error");
            error = true;
            isValid = false;
        } else {
            $("#error-title").text('');

        }

        var x = new Date($("#bdate").val());
        var Cnow = new Date();
        if ($("#bdate").val() == "") {
            $("#bdate").focus();
        } else if (Cnow.getFullYear() - x.getFullYear() < 18) {

            $("#error-bdate").text('You Are Not 18 Year.');
            error = true;
            isValid = false;
        }


        // Gender
        if ($("#gender option:selected").val() == '') {
            $("#error-gender").text('Please Select Gender.');
            $("#gender").addClass("box_error");
            error = true;
            isValid = false;
        } else {
            $("#error-gender").text('');

        }

        // phone
        if ($("#phone").val() == '') {
            $("#error-phone").text('Enter your Mobile number.');
            $("#phone").addClass("box_error");
            error = true;
            isValid = false;
        } else {
            var phone = $("#phone").val();
            if (phone != phone) {
                $("#error-phone").text('Mobile number is required.');
                error = true;
                isValid = false;
            } else {
                $("#error-phone").text('');

            }
            if (phone.length != 10) {
                $("#error-phone").text("Mobile number Must be 10 digit.");
                $("#phone").addClass("box_error");
                error = true;
                isValid = false;
            } else {
                $("#phone").removeClass("box_error");
            }
            if (!/^[0-9]+$/.test(phone)) {
                $("#error-phone").text("Mobile number in Digits only.");
                $("#phone").addClass("box_error");
                error = true;
                isValid = false;
            } else {
                $("#phone").removeClass("box_error");
            }
        }
    } else if (fieldSetId === '3') {

        // Country
        if ($("#country option:selected").val() == '') {
            $("#error-country").text('Please Select Country.');
            $("#country").addClass("box_error");
            error = true;
            isValid = false;
        } else {
            $("#error-country").text('');

        }

        // State
        if ($("#state option:selected").val() == '') {
            $("#error-state").text('Please Select State.');
            $("#state").addClass("box_error");
            error = true;
            isValid = false;
        } else {
            $("#error-state").text('');

        }


        // District
        if ($("#district option:selected").val() == '') {
            $("#error-district").text('Please Select District.');
            $("#district").addClass("box_error");
            error = true;
            isValid = false;
        } else {
            $("#error-district").text('');
        }


        // mandal
        if ($("#mandal option:selected").val() == '') {
            $("#error-mandal").text('Please Select Mandal.');
            $("#mandal").addClass("box_error");
            error = true;
            isValid = false;
        } else {
            $("#error-mandal").text('');
        }


        // village
        if ($("#village option:selected").val() == '') {
            $("#error-village").text('Please Select village.');
            $("#district").addClass("box_error");
            error = true;
            isValid = false;
        } else {
            $("#error-village").text('');
        }

        // zip Code
        var zip = $("#zip").val();
        if (zip != zip) {
            $("#error-zip").text('Enter your Zip Code.');
            $("#zip").addClass("box_error");
            error = true;
            isValid = false;
        } else {
            $("#error-zip").text('');
            error = false;
        }
        if (zip.length < 5) {
            $("#error-zip").text("Zip Code more than 5 digit.");
            $("#zip").addClass("box_error");
            error = true;
            isValid = false;
        } else {
            $("#zip").removeClass("box_error");
        }
        if (!/^[0-9]+$/.test(zip)) {
            $("#error-zip").text("Zip Code in Digits only.");
            $("#zip").addClass("box_error");
            error = true;
            isValid = false;
        } else {
            $("#zip").removeClass("box_error");
        }

        if ($("#totalLand").val() == '0.0') {
            $("#error-totalLand").text("Please enter total land in Acres.");
            $("#totalLand").addClass("box_error");
            error = true;
            isValid = false;
        } else {
            $("#error-totalLand").text('');
            $("#totalLand").removeClass("box_error");
        }
        if ($("#soilType option:selected").val() == '') {
            $("#error-soilType").text("Please select soil type.");
            $("#soilType").addClass("box_error");
            error = true;
            isValid = false;
        } else {
            $("#error-soilType").text('');
        }

        if ($("#ppbNo").val() == '') {
            $("#error-ppbNo").text("Please enter PPB NO.");
            $("#ppbNo").addClass("box_error");
            error = true;
            isValid = false;
        } else {
            $("#error-ppbNo").text('');
        }

        var maxSizeInBytes = 2 * 1024 * 1024; // 2 MB
        var fileInput = document.getElementById("ppb_doc");

        if (fileInput.files.length > 0) {
            var fileSize = fileInput.files[0].size;

            if (fileSize > maxSizeInBytes) {
                $("#error-ppb-doc").text("File size exceeds the maximum limit of 2 MB.");
                $("#ppb_doc").addClass("box_error");
                error = true;
                isValid = false;
            } else {
                $("#error-ppb-doc").text('');
            }
        }

    } else if (fieldSetId === '4') {

        if ($("#surveyNos").val() == '') {
            $("#error-survey-nos").text("Please enter survey Nos.");
            $("#surveyNos").addClass("box_error");
            error = true;
            isValid = false;
        } else {
            $("#error-survey-nos").text('');
            $("#surveyNos").removeClass("box_error");
        }
        if ($("#currentCrop option:selected").val() == '') {
            $("#error-current-crop").text('Please select current crop.');
            $("#currentCrop").addClass("box_error");
            error = true;
        } else {
            $("#error-current-crop").text('');
            error = false;
        }

        if ($("#totalLandHoldingInAcres").val() == '0.0') {
            $("#error-total-land-holding-acres").text("Please enter total land in Acres.");
            $("#totalLandHoldingInAcres").addClass("box_error");
            error = true;
            isValid = false;
        } else {
            $("#error-total-land-holding-acres").text('');
            $("#totalLandHoldingInAcres").removeClass("box_error");
        }
        var netIncome = $("#netIncome").val();

        if (netIncome == '0.0' || netIncome == '') {
            $("#error-net-income-farmer").text('Please enter net Income.');
            $("#netIncomeFarmer").addClass("box_error");
            error = true;
            isValid = false;
        } else {
            $("#error-net-income-farmer").text('');
            error = false;
        }
    } else if (fieldSetId === '5') {
        var surveyNos = $("#proposedSurveyNos").val();

        if (surveyNos == '') {
            $("#error-proposed-survey-nos").text('Please enter Survey Nos.');
            $("#proposedSurveyNos").addClass("box_error");
            error = true;
            isValid = false;
        } else {
            $("#error-proposed-survey-nos").text('');
            error = false;
        }
        var proposedArea = $("#proposedArea").val();

        if (proposedArea == '0.0' || proposedArea == '') {
            $("#error-proposed-acres").text('Please enter proposed area in Acres.');
            $("#proposedAreaInAcres").addClass("box_error");
            error = true;
            isValid = false;
        } else {
            $("#error-proposed-acres").text('');
            error = false;
        }
        if ($("#plantationType option:selected").val() == '') {
            $("#error-plantation-type").text('Please select plantation type.');
            $("#plantationType").addClass("box_error");
            error = true;
            isValid = false;
        } else {
            $("#error-plantation-type").text('');
            error = false;
        }
    } else if (fieldSetId === '6') {
        if ($("#waterSource option:selected").val() == '') {
            $("#error-water-source").text('Please select water source.');
            $("#waterSource").addClass("box_error");
            error = true;
            isValid = false;
        } else {
            $("#error-water-source").text('');
            error = false;
        }
    }

    return isValid;
}