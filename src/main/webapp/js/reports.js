"use strict";
var ssp = ssp || {};

(function($, fluid) {
	ssp.ReportSelector = function(container, options) {

		// load the report form
		var loadReportForm = function(containerId) {
			// hide all forms
			var hideableForms = that.locate('hideableform');
			$(hideableForms).filter(":visible").toggle(1500);

			// show the new form
			var container = that.locate(containerId);
			$(container).toggle(1500);
		}

		var loadGroupInput = function(url, container) {
			$.getJSON(url, function(data) {
				$.each(data.rows, function(i, row) {
					addSelectItem(row.id, row.name, container);
				});

			}).error(function(jqXHR, textStatus, errorThrown) {
				alert(jqXHR + " " + textStatus + " " + errorThrown);
			});
		}

		var loadTextForm = function(url, container) {
			$.getJSON(url, function(data) {
				$.each(data.rows, function(i, row) {
					$.each(data.rows, function(i, row) {
						$(container).html(row.text);
					});
				});

			}).error(function(jqXHR, textStatus, errorThrown) {
				alert(jqXHR + " " + textStatus + " " + errorThrown);
			});
		}

		// load Addresses
		var loadAddressesForm = function() {
			// var addressesForm = that.locate('addressesForm');
			loadGroupInput("/ssp/api/1/reference/programStatus/", that
					.locate('programStatusGroup'));
			loadGroupInput("/ssp/api/1/reference/studentType/", that
					.locate('studentTypeGroup'));
			loadGroupInput("/ssp/api/1/reference/specialServiceGroup/", that
					.locate('specialServiceGroup'));
			loadGroupInput("/ssp/api/1/reference/referralSource/", that
					.locate('referralSourceGroup'));

			loadTextForm(
					"/ssp/api/1/reference/confidentialityDisclosureAgreement/",
					that.locate('confidentialityAgreementFormContent'));
		}

		function addSelectItem(uid, name, container) {
			var inputs = container.find('input');
			var id = inputs.length + 1;

			var html = '<option value="' + uid + '">' + name + '</option>';
			container.append($(html));
		}

		$(document).ready(function() {
			var calendarType = that.locate('calendarType');
			$(calendarType).datepicker({
				showOn : 'button',
				buttonImageOnly : true,
				buttonImage : '/ssp/images/calendar.gif'
			});
		});

		// construct the new component
		var that = fluid.initView('ssp.ReportSelector', container, options);

		var reportsSelectChange = function() {
			var reportsSelect = that.locate('reportsSelect');
			loadReportForm(reportsSelect.val());
		};

		var printConfForm = that.locate('printConfForm');
		$(printConfForm).attr("href", "javascript:void( 0 )").click(
				function() {
					var confidentialityAgreementFormContent = that
							.locate('confidentialityAgreementFormContent');
					// Print the DIV.
					$(confidentialityAgreementFormContent).print();

					// Cancel click event.
					return (false);
				});

		// Courses Select
		var reportsSelect = that.locate('reportsSelect');
		reportsSelect
				.append('<option value="addressesForm">Address Labels Report</option>');
		reportsSelect
				.append('<option value="specialServicesForm">Special Services Report</option>');
		reportsSelect
				.append('<option value="confidentialityAgreementForm">Confidentiality Agreement</option>');
		reportsSelect.change(reportsSelectChange);
		loadAddressesForm();
		reportsSelectChange();

	}

	// defaults
	fluid
			.defaults(
					'ssp.ReportSelector',
					{
						selectors : {
							reportsSelect : '.reports-select',
							reportFormBody : '.reports-form-body',
							addressesForm : '.addresses-form',
							specialServicesForm : '.special-services-form',
							confidentialityAgreementForm : '.confidentiality-agreement-form',
							confidentialityAgreementFormContent : '.confidentiality-agreement-form-content',
							programStatusGroup : '.input-program-status-group',
							studentTypeGroup : '.input-student-type-group',
							specialServiceGroup : '.input-special-service-group',
							referralSourceGroup : '.input-referral-source-group',
							calendarType : '.input-calendar-type',
							hideableform : '.hideable-form',
							printConfForm : '.print-conf-form'
						}
					});

})(jQuery, fluid);