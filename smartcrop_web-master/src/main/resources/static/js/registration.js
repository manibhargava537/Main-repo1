//    buildCountryDropDown();

  $("#country").on('change', function() {
    buildCountryStateDropDown();
  });

  $("#state").on('change', function() {
    buildStateDistrictDropDown();
  });

  $("#district").on('change', function() {
    buildDistrictMandalDropDown();
  });

  $("#mandal").on('change', function() {
    buildMandalVillageDropDown();
  });


//buildCountryDropDown = function() {
//  let countryList = stateDistrictList.countries;
//  let firstOption = "<option value='0' selected=' ' disabled=''>--Select--</option>";
//  $("#country").append(firstOption);
//  $.each(countryList, (counter, country) => {
//    let option = "<option value = " + country.country + ">" + country.country + "</option>";
//    $("#country").append(option);
//  });
//};

buildCountryStateDropDown = function() {
//  let countryList = stateDistrictList.countries;
  $("#state").empty();
  $("#district").empty();
 $("#mandal").empty();
  $("#village").empty();
  let firstOption = "<option value='0' selected=' ' disabled=''>--Select--</option>";
  $("#state").append(firstOption);
  let selectedCountry = $("#country").val();

//  debugger;
  $.each(stateDistrictList, (counter, country) => {
    if (selectedCountry == country.country) {
      let stateList = country.states;
      $.each(stateList, (counter, state) => {
        let option = "<option value = '" + state.state + "'>" + state.state + "</option>";
        $("#state").append(option);
      });
    }

  });
};



buildStateDistrictDropDown = function() {
//  let countryList = stateDistrictList.countries;
  $("#district").empty();
   $("#mandal").empty();
    $("#village").empty();
  let firstOption = "<option value='0' selected=' ' disabled=''>--Select--</option>";
  $("#district").append(firstOption);
  let selectedState = $("#state").val();
  let selectedCountry = $("#country").val();
  $.each(stateDistrictList, (counter, country) => {
    if (selectedCountry == country.country) {
      let stateList = country.states;

      $.each(stateList, (counter, state) => {
      console.log(selectedState);
        if (state.state == selectedState) {
          let districtList = state.districts;

          $.each(districtList, (counter, district) => {
            if (typeof(district) === 'string') {
              let option = "<option value = '" + district + "'>" + district + "</option>";
              $("#district").append(option);
            } else {

              let option = "<option value = " + district.district + ">" + district.district + "</option>";
              $("#district").append(option);

            }
          });
        }

      });
    }

  });
};


buildDistrictMandalDropDown = function() {
//  let countryList = stateDistrictList.countries;
  $("#mandal").empty();
  $("#village").empty();
  let firstOption = "<option value='0' selected=' ' disabled=''>--Select--</option>";
  $("#mandal").append(firstOption);
  let selectedState = $("#state").val();
  let selectedCountry = $("#country").val();
  let selectedDistrict = $("#district").val();
  $.each(stateDistrictList, (counter, country) => {
    if (selectedCountry == country.country) {
      let stateList = country.states;

      $.each(stateList, (counter, state) => {
        if (state.state == selectedState) {
          let districtList = state.districts;
          $.each(districtList, (counter, district) => {
            if (typeof(district) === 'string') {
              console.log("hide mandal and village");
               $('.zip').fadeIn();
                  $('.mandal').fadeOut();
                  $('.village').fadeOut();
            } else {
              /**
              	 "mandals": [{
                    "mandal": "Adilabad Rural",
                    "villages": [
              **/
              if (selectedDistrict == district.district) {
                let mandalList = district.mandals;
                $.each(mandalList, (counter, mandal) => {
                  let option = "<option value = '" + mandal.mandal + "'>" + mandal.mandal + "</option>";
                  $("#mandal").append(option);
                });

              }


            }
          });
        }

      });
    }

  });

};



buildMandalVillageDropDown = function() {
//  let countryList = stateDistrictList.countries;
  $("#village").empty();
  let firstOption = "<option value='0' selected=' ' disabled=''>--Select--</option>";
  $("#village").append(firstOption);
  let selectedState = $("#state").val();
  let selectedCountry = $("#country").val();
  let selectedDistrict = $("#district").val();
  let selectedMandal = $("#mandal").val();
  $.each(stateDistrictList, (counter, country) => {
    if (selectedCountry == country.country) {
      let stateList = country.states;

      $.each(stateList, (counter, state) => {
        if (state.state == selectedState) {
          let districtList = state.districts;
          $.each(districtList, (counter, district) => {
            if (typeof(district) === 'string') {
              console.log("hide mandal and village")
            } else {
              /**
              	 "mandals": [{
                    "mandal": "Adilabad Rural",
                    "villages": [
              **/
              if (selectedDistrict == district.district) {
                let mandalList = district.mandals;
                console.log("selectedMandal =" +selectedMandal);
                $.each(mandalList, (counter, mandal) => {
                	console.log("mandal =" +mandal.mandal);

                  if (selectedMandal == mandal.mandal) {
                    let villageList = mandal.villages;
                    console.log(villageList);
                    $.each(villageList, (counter, village) => {
                      let option = "<option value = '" + village + "'>" + village + "</option>";
                      $("#village").append(option);
                    });

                  }
                });

              }


            }
          });
        }

      });
    }

  });

};