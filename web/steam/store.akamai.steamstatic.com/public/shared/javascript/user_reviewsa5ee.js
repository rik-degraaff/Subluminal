
function UserReview_Rate( recommendationID, bRateUp, baseURL, callback )
{
	$J.post( baseURL + '/userreviews/rate/' + recommendationID,{
				'rateup' : bRateUp,
				'sessionid' : g_sessionID
	}).done( function( results ) {
		if ( results.success == 1 )
		{
			callback( results );
		}
		else if ( results.success == 21 )
		{
			ShowAlertDialog( 'Fehler', 'Sie müssen angemeldet sein, um diese Aktion durchzuführen.' );
		}
		else if ( results.success == 15 )
		{
			ShowAlertDialog( 'Fehler', 'Ihr Account verfügt nicht über die nötigen Rechte, um diese Aktion durchzuführen.' );
		}
		else if ( results.success == 24 )
		{
			ShowAlertDialog( 'Fehler', 'Ihr Account erfüllt nicht die Voraussetzungen, um diese Funktion zu nutzen. <a class="whiteLink" href="https://help.steampowered.com/de/wizard/HelpWithLimitedAccount" target="_blank" rel="noreferrer">Besuchen Sie den Steam-Support</a>, um weitere Informationen zu erhalten.' );
		}
		else
		{
			ShowAlertDialog( 'Fehler', 'Beim Verarbeiten Ihrer Anfrage ist ein Fehler aufgetreten: ' + results.success );
		}
	} );
}

function UserReview_VoteTag( recommendationID, tagID, bRateUp, baseURL, callback )
{
	$J.post( baseURL + '/userreviews/votetag/' + recommendationID,{
		'tagid' : tagID,
		'rateup' : bRateUp,
		'sessionid' : g_sessionID
	}).done( function( results ) {
		if ( results.success == 1 )
		{
			callback( results );
		}
		else if ( results.success == 21 )
		{
			ShowAlertDialog( 'Fehler', 'Sie müssen angemeldet sein, um diese Aktion durchzuführen.' );
		}
		else if ( results.success == 15 )
		{
			ShowAlertDialog( 'Fehler', 'Ihr Account verfügt nicht über die nötigen Rechte, um diese Aktion durchzuführen.' );
		}
		else if ( results.success == 24 )
		{
			ShowAlertDialog( 'Fehler', 'Ihr Account erfüllt nicht die Voraussetzungen, um diese Funktion zu nutzen. <a class="whiteLink" href="https://help.steampowered.com/de/wizard/HelpWithLimitedAccount" target="_blank" rel="noreferrer">Besuchen Sie den Steam-Support</a>, um weitere Informationen zu erhalten.' );
		}
		else
		{
			ShowAlertDialog( 'Fehler', 'Beim Verarbeiten Ihrer Anfrage ist ein Fehler aufgetreten: ' + results.success );
		}
	} );
}

function UserReview_Report( recommendationID, baseURL, callback )
{
	var dialog = ShowPromptWithTextAreaDialog( 'Review melden', '', null, null, 1000 );
	var explanation = $J('<div/>', { 'class': 'user_review_report_dialog_explanation' } );
	explanation.html( 'Geben Sie den Grund an, weshalb Sie dieses Review wegen eines Verstoßes gegen die Steam-Nutzungsvereinbarung oder die Richtlinien zum Onlineverhalten\n melden. Dies kann nicht rückgängig gemacht werden.' );

	var textArea = dialog.m_$Content.find( 'textarea' );
	textArea.addClass( "user_review_report_dialog_text_area" );
	textArea.parent().before( explanation );

	dialog.done( function( note ) {
		if ( !note )
		{
			return;
		}
		note = v_trim( note );
		if ( note.length < 1 )
		{
			return;
		}
		$J.post( baseURL + '/userreviews/report/' + recommendationID, {
					'reportnote' : note,
					'sessionid' : g_sessionID
		}).done( function( results ) {
			if ( results.success == 1 )
			{
				callback( results );
			}
			else if ( results.success == 21 )
			{
				ShowAlertDialog( 'Fehler', '##UserReviews_Error_NotLoggedIn_Text' );
			}
			else
			{
				ShowAlertDialog( 'Fehler', 'Beim Verarbeiten Ihrer Anfrage ist ein Fehler aufgetreten: ' + results.success );
			}
		} );
	} );
}

function UserReview_ShowUpdateReviewDialog( recommendationID, existingText, baseURL )
{
	var dialog = ShowPromptWithTextAreaDialog( 'Review aktualisieren', existingText, null, null, 4096 );

	dialog.done( function( note ) {
		if ( !note )
		{
			return;
		}
		note = v_trim( note );
		if ( note.length < 1 )
		{
			return;
		}
		UserReview_Update_Text( recommendationID, note, baseURL, function( results ) { top.location.reload(); } );
	} );
}

function UserReview_Update( recommendationID, params, baseURL, callback )
{
	params['sessionid'] = g_sessionID;
	$J.post( baseURL + '/userreviews/update/' + recommendationID, params )
	.done( function( results ) {
		if ( results.success == 1 )
		{
			if ( callback )
			{
				callback( results );
			}
		}
		else
		{
			ShowAlertDialog( 'Fehler', 'Beim Verarbeiten Ihrer Anfrage ist ein Fehler aufgetreten: ' + results.success );
		}
	} );
}

function UserReview_Update_Visibility( recommendationID, is_public, baseURL, callback )
{
	UserReview_Update( recommendationID, { 'is_public' : is_public }, baseURL, callback );
}

function UserReview_Update_Language( recommendationID, language, baseURL, callback )
{
	UserReview_Update( recommendationID, { 'language' : language }, baseURL, callback );
}

function UserReview_Moderate( recommendationID, params, baseURL, callback )
{
	params['sessionid'] = g_sessionID;
	$J.post( baseURL + '/userreviews/moderate/' + recommendationID, params )
		.done( function( results ) {
			if ( results.success != 1 )
			{
				var dialog = ShowAlertDialog( 'Fehler', 'Beim Verarbeiten Ihrer Anfrage ist ein Fehler aufgetreten: ' + results.success );
				dialog.done( function() {
					if ( callback )
					{
						callback( results );
					}
				} );
			}
			else
			{
				if ( callback )
				{
					callback( results );
				}
			}
		} );
}

function UserReview_ClearDeveloperFlag( recommendationID, baseURL, callback )
{
	var dialog = ShowConfirmDialog( 'Entwicklermarkierung entfernen', 'Dieses Review wurde vom Entwickler markiert. Sind Sie sicher, dass Sie diesen Status zurücksetzen möchten?' );
	dialog.done( function() {
		$J.post( baseURL + '/userreviews/cleardeveloperflag/' + recommendationID, {'sessionid' : g_sessionID} )
		.done( function( results ) {
			if ( results.success == 1 )
			{
				if ( callback )
				{
					callback( results );
				}
			}
			else
			{
				ShowAlertDialog( 'Fehler', 'Beim Verarbeiten Ihrer Anfrage ist ein Fehler aufgetreten: ' + results.success );
			}
		} );
	});
}

function UserReview_SetDeveloperResponse( recommendationID, recommendation, baseURL, callback )
{
	var dialog = ShowPromptWithTextAreaDialog( 'Eine Antwort verfassen', recommendation.developer_response, null, null, 8000 );
	var explanation = $J('<div/>', { 'class': 'user_review_report_dialog_explanation' } );
	explanation.html( 'Sie können von offizieller Seite auf dieses Review antworten. Diese Antwort wird für jeden sichtbar sein, der dieses Review sehen kann, und wird als Entwicklerkommentar gekennzeichnet.' );

	var textArea = dialog.m_$Content.find( 'textarea' );
	textArea.addClass( "user_review_report_dialog_text_area" );
	textArea.parent().before( explanation );

	dialog.done( function( note ) {
		$J.post( baseURL + '/userreviews/setdeveloperresponse/' + recommendationID,{
					'developer_response' : note,
					'sessionid' : g_sessionID
		}).done( function( results ) {
			if ( results.success == 1 )
			{
				callback( results );
			}
			else
			{
				ShowAlertDialog( 'Fehler', 'Beim Verarbeiten Ihrer Anfrage ist ein Fehler aufgetreten: ' + results.success );
			}
		} );
	} );
}

function UserReview_ShowReportsDialog( recommendationID, baseURL )
{
	$J.post( baseURL + '/userreviews/ajaxgetreports/' + recommendationID,{ 'sessionid' : g_sessionID } )
	.done( function( results ) {
		if ( results.success == 1 )
		{
			var container = $J('<div/>', {'class': 'review_reports' } );
			var reports = results.reports;

			{
				var reportDiv = $J('<div/>', {'class': 'review_report header' } );
				var divReporter = $J('<div/>', {'class': 'review_report_data' } ).append( 'Reporter' );
				reportDiv.append( divReporter );
				var divDescription = $J('<div/>', {'class': 'review_report_data description' } ).append( 'Report Description' );
				reportDiv.append( divDescription );
				var divWeight = $J('<div/>', {'class': 'review_report_data' } ).append( 'Weight' );
				reportDiv.append( divWeight );
				var divWasReset = $J('<div/>', {'class': 'review_report_data' } ).append( 'Cleared?' );
				reportDiv.append( divWasReset );
				var divTime = $J('<div/>', {'class': 'review_report_data' } ).append( 'Date' );
				reportDiv.append( divTime );
				var divClear = $J('<div/>', {'style': 'clear: left' } );
				reportDiv.append( divClear );
				container.append( reportDiv );
			}

			for ( var i = 0; i < reports.length; ++i )
			{
				var report = reports[i];

				var reportDiv = $J('<div/>', {'class': 'review_report' } );
					var divReporter = $J('<div/>', {'class': 'review_report_data' } ).append( $J('<a/>', {'href': report.reporter_url, 'text': report.reporter, 'target': '_blank' } ) );
					reportDiv.append( divReporter );
					var divDescription = $J('<div/>', {'class': 'review_report_data description' } ).append( report.description );
					reportDiv.append( divDescription );
					var divWeight = $J('<div/>', {'class': 'review_report_data' } ).append( report.weight );
					reportDiv.append( divWeight );
					var divWasReset = $J('<div/>', {'class': 'review_report_data' } ).append( report.was_reset ? 'Yes' : 'No' );
					reportDiv.append( divWasReset );
					var divTime = $J('<div/>', {'class': 'review_report_data' } ).append( report.time_string );
					reportDiv.append( divTime );
					var divClear = $J('<div/>', {'style': 'clear: left' } );
					reportDiv.append( divClear );
				container.append( reportDiv );
			}
			var dialog = ShowAlertDialog( 'Meldungen zurücksetzen', container );
		}
	} );
}

function UserReview_ShowClearReportsDialog( recommendationID, baseURL, callback )
{
	var dialog = ShowConfirmDialog( 'Meldungen zurücksetzen', 'Sind Sie sicher, dass Sie alle Meldungen zurücksetzen möchten? Dies kann nicht rückgängig gemacht werden!' );
	dialog.done( function() {
		UserReview_Moderate( recommendationID, { 'clear_reports' : 1 }, baseURL, callback);
	});
}

function UserReview_Moderate_SetBanStatus( recommendationID, force_hidden, baseURL, callback, strModerationNote )
{
	UserReview_Moderate( recommendationID, { 'force_hidden' : force_hidden, 'moderation_note' : strModerationNote }, baseURL, callback );
}

function UserReview_Moderate_SetDeveloperFlag( recommendationID, flagged_by_developer, baseURL, callback )
{
	UserReview_Moderate( recommendationID, { 'flagged_by_developer' : flagged_by_developer }, baseURL, callback );
}

function UserReview_Moderate_SetQuality( recommendationID, quality, baseURL, callback )
{
	UserReview_Moderate( recommendationID, { 'review_quality' : quality }, baseURL, callback );
}
