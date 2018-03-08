
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
			ShowAlertDialog( 'Σφάλμα', 'Πρέπει να είστε συνδεδεμένος για την εκτέλεση αυτής της ενέργειας.' );
		}
		else if ( results.success == 15 )
		{
			ShowAlertDialog( 'Σφάλμα', 'Ο λογαριασμός σας δεν έχει αρκετά προνόμια για να εκτελέσει αυτή την πράξη.' );
		}
		else if ( results.success == 24 )
		{
			ShowAlertDialog( 'Σφάλμα', 'Ο λογαριασμός σας δεν πληροί τις προϋποθέσεις για να χρησιμοποιήσει αυτή τη λειτουργία. <a class="whiteLink" href="https://help.steampowered.com/el/wizard/HelpWithLimitedAccount" target="_blank" rel="noreferrer">Επισκεφθείτε την Υποστήριξη Steam</a> για περισσότερες πληροφορίες.' );
		}
		else
		{
			ShowAlertDialog( 'Σφάλμα', 'Υπήρξε ένα σφάλμα κατά την προσπάθεια επεξεργασίας του αιτήματός σας:' + results.success );
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
			ShowAlertDialog( 'Σφάλμα', 'Πρέπει να είστε συνδεδεμένος για την εκτέλεση αυτής της ενέργειας.' );
		}
		else if ( results.success == 15 )
		{
			ShowAlertDialog( 'Σφάλμα', 'Ο λογαριασμός σας δεν έχει αρκετά προνόμια για να εκτελέσει αυτή την πράξη.' );
		}
		else if ( results.success == 24 )
		{
			ShowAlertDialog( 'Σφάλμα', 'Ο λογαριασμός σας δεν πληροί τις προϋποθέσεις για να χρησιμοποιήσει αυτή τη λειτουργία. <a class="whiteLink" href="https://help.steampowered.com/el/wizard/HelpWithLimitedAccount" target="_blank" rel="noreferrer">Επισκεφθείτε την Υποστήριξη Steam</a> για περισσότερες πληροφορίες.' );
		}
		else
		{
			ShowAlertDialog( 'Σφάλμα', 'Υπήρξε ένα σφάλμα κατά την προσπάθεια επεξεργασίας του αιτήματός σας:' + results.success );
		}
	} );
}

function UserReview_Report( recommendationID, baseURL, callback )
{
	var dialog = ShowPromptWithTextAreaDialog( 'Αναφορά κριτικής', '', null, null, 1000 );
	var explanation = $J('<div/>', { 'class': 'user_review_report_dialog_explanation' } );
	explanation.html( 'Εισάγετε το λόγο που αναφέρετε αυτή την κριτική για παραβίαση των Όρων χρήσης ή της Διαδικτυακής συμπεριφοράς Steam. Αυτή η ενέργεια δεν μπορεί να αναιρεθεί.' );

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
				ShowAlertDialog( 'Σφάλμα', '##UserReviews_Error_NotLoggedIn_Text' );
			}
			else
			{
				ShowAlertDialog( 'Σφάλμα', 'Υπήρξε ένα σφάλμα κατά την προσπάθεια επεξεργασίας του αιτήματός σας:' + results.success );
			}
		} );
	} );
}

function UserReview_ShowUpdateReviewDialog( recommendationID, existingText, baseURL )
{
	var dialog = ShowPromptWithTextAreaDialog( 'Ενημέρωση κριτικής', existingText, null, null, 4096 );

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
			ShowAlertDialog( 'Σφάλμα', 'Υπήρξε ένα σφάλμα κατά την προσπάθεια επεξεργασίας του αιτήματός σας:' + results.success );
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
				var dialog = ShowAlertDialog( 'Σφάλμα', 'Υπήρξε ένα σφάλμα κατά την προσπάθεια επεξεργασίας του αιτήματός σας:' + results.success );
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
	var dialog = ShowConfirmDialog( 'Εκκαθάριση αιτίας επισήμανσης δημιουργού', 'Αυτή η κριτική επισημάνθηκε από τον δημιουργό. Θέλετε σίγουρα να καταργήσετε αυτήν την κατάσταση;' );
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
				ShowAlertDialog( 'Σφάλμα', 'Υπήρξε ένα σφάλμα κατά την προσπάθεια επεξεργασίας του αιτήματός σας:' + results.success );
			}
		} );
	});
}

function UserReview_SetDeveloperResponse( recommendationID, recommendation, baseURL, callback )
{
	var dialog = ShowPromptWithTextAreaDialog( 'Γράψτε μια απάντηση', recommendation.developer_response, null, null, 8000 );
	var explanation = $J('<div/>', { 'class': 'user_review_report_dialog_explanation' } );
	explanation.html( 'Μπορείτε να επιλέξετε να απαντήσετε σε αυτή την κριτική επίσημα. Αυτή η απάντηση θα είναι ορατή σε οποιονδήποτε που μπορεί να δει αυτή την κριτική και θα επισημανθεί ως του δημιουργού.' );

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
				ShowAlertDialog( 'Σφάλμα', 'Υπήρξε ένα σφάλμα κατά την προσπάθεια επεξεργασίας του αιτήματός σας:' + results.success );
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
			var dialog = ShowAlertDialog( 'Εκκαθάριση αναφορών', container );
		}
	} );
}

function UserReview_ShowClearReportsDialog( recommendationID, baseURL, callback )
{
	var dialog = ShowConfirmDialog( 'Εκκαθάριση αναφορών', 'Θέλετε σίγουρα να εκκαθαρίσετε όλες τις αναφορές; Αυτή η ενέργεια δεν μπορεί να αναιρεθεί!' );
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
