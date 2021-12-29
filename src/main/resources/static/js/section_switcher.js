
const VKUSVILL_SECTION_NAME = "Vkusvill_section"

function switchToVkusvill(){

    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            //Success
            loadSection(VKUSVILL_SECTION_NAME);
        }
    };

    xhttp.open("GET", "/check/vkusvill_auth");
    xhttp.send();
}

function loadSection(section_name) {
    let content = getSectionContent(section_name);
    $("#section-container").append(content);
}

function getSectionContent(VKUSVILL_SECTION_NAME) {
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            //Success
            return processContentResponse(xhttp.response);

        }
    };

    xhttp.open("GET", "/content/vkusvill");
    xhttp.responseType = "json";
    xhttp.send();
}

function processContentResponse(response) {

    let vkusvill_section;

    let addresses = response['addresses'];
    let lists = response['lists']; //Lists of products, first list its list of favorite products
    let mealsList = response['mealsList'];//Meals list,which sorted by favourite products list and after by alphabet


    $.get("html/sections/Vkusvill.html", function (data) {

        let address_template = data.find("#address_template");
        let lists_slot_template = data.find("#lists_slot_template");
        let mealSlot = data.find("#meal_slot_template");

        //Insert delivery addresses in list
        for (let address of addresses) {
            let deliveryList = data.find("#delivery-addresses-list");
            let newAddressSlot = address_template.clone();
            newAddressSlot.find("a").text(address);
            deliveryList.append(newAddressSlot);
        }

        let mealListsDropdown = data.find("#lists_template");

        //Insert favourite product list in name of dropdown button
        if(lists != null && lists[0] != null){
            mealListsDropdown.find("#chose-meal-list").text(lists[0]);
        }

        //Insert another buy lists
        for (let i = 1;i < lists.length;i++){
            let newListSlot = lists_slot_template.clone();
            newListSlot.find("p").text(lists[i]);
            mealListsDropdown.find("#another-meal-lists").append(newListSlot);
        }

        //Insert meal slots in list
        for(let meal of mealsList){
            let list = data.find("#meals-list");
            let newMealSlot = mealSlot.clone();
            newMealSlot.find(".meal-name").text(meal.name);
            newMealSlot.find(".meal-describe").text(meal.describe);
            newMealSlot.find(".meal-price").text(meal.price)
            newMealSlot.find(".meal-image").src(meal.img);

            list.append(newMealSlot);
        }

        vkusvill_section = data;
    }, "html");

    return vkusvill_section;
}
