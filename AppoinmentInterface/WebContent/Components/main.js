$(document).ready(function() {
	if ($("#alertSuccess").text().trim() == "") {
		$("#alertSuccess").hide();
	}
	$("#alertError").hide();
});

$(document).on("click", "#btnSave", function(event) {
	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();
	// Form validation-------------------
	var status = validateItemForm();
	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}

	var type = ($("#hidItemIDSave").val() == "") ? "POST" : "PUT";

	$.ajax({
		url : "AppointmentAPI",
		type : type,
		data : $("#formApp").serialize(),
		dataType : "text",
		complete : function(response, status) {
			onItemSaveComplete(response.responseText, status);
		}
	});


});



function onItemSaveComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#divItemsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}
	$("#hidItemIDSave").val("");
	$("#formApp")[0].reset();
}

$(document).on("click", ".btnRemove", function(event) {
	$.ajax({
		url : "AppointmentAPI",
		type : "DELETE",
		data : "aId=" + $(this).data("itemid"),
		dataType : "text",
		complete : function(response, status) {
			onItemDeleteComplete(response.responseText, status);
		}
	});
});

function onItemDeleteComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			$("#divItemsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}

$(document).on("click",".btnUpdate",function(event) {
			$("#hidItemIDSave").val(
			$(this).closest("tr").find('#hidItemIDUpdate').val());
			$("#aId").val($(this).closest("tr").find('td:eq(0)').text());
			$("#pname").val($(this).closest("tr").find('td:eq(1)').text());
			$("#pId").val($(this).closest("tr").find('td:eq(2)').text());
			$("#dName").val($(this).closest("tr").find('td:eq(3)').text());
			$("#dMobile").val($(this).closest("tr").find('td:eq(4)').text());
		});

function validateItemForm() {
	// CODE
//	if ($("#id").val().trim() == "") {
//		return "Insert Item Doctor Id.";
//	}
	// NAME
	if ($("#aId").val().trim() == "") {
		return "Insert Item Name.";
	}

	// PRICE-------------------------------
	if ($("#pname").val().trim() == "") {
		return "Insert Item Address.";
	}
	
	if ($("#pId").val().trim() == "") {
		return "Insert Item Description.";
	}
	
	if ($("#dMobile").val().trim() == "") {
		return "Insert Item Description.";
	}
	
	// is numerical value
	var tmpPrice = $("#dMobile").val().trim();
	
	if (!$.isNumeric(tmpPrice)) {
		return "Insert a numerical value.";

	}

	return true;
}
