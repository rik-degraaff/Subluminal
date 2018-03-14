"use strict";


function ShowEmbedWidget( )
{
	$J('#widget_create').show();
	$J('#widget_finished').hide();

	var $Content = $J('#EmbedModal');
	$Content.detach();
	$Content.show();

	var deferred = new jQuery.Deferred();
	var fnOK = function() { deferred.resolve(); };

	var Modal = _BuildDialog( "\u0394\u03b7\u03bc\u03b9\u03bf\u03c5\u03c1\u03b3\u03af\u03b1 \u03b4\u03b9\u03b1\u03c6\u03ae\u03bc\u03b9\u03c3\u03b7\u03c2 \u03b3\u03b9\u03b1 \u03c0\u03c1\u03bf\u03c3\u03b8\u03ae\u03ba\u03b7", $Content, [], fnOK, {} );
	deferred.always( function() { Modal.Dismiss(); } );
	Modal.Show();

	// attach the deferred's events to the modal
	deferred.promise( Modal );

	Modal.always(
		function() {
			// save it away again for later
			$Content.hide();
			$J(document.body).append( $Content );
		}
	);
}

function ShowShareDialog( )
{
	var $Content = $J('#ShareModal');
	$Content.detach();
	$Content.show();

	ShowAlertDialog( "\u039a\u03bf\u03b9\u03bd\u03bf\u03c0\u03bf\u03af\u03b7\u03c3\u03b7", $Content).always(
		function() {
			// save it away again for later
			$Content.hide();
			$J(document.body).append( $Content );
		}
	);

}

function CreateWidget( nAppId )
{
	$J('#widget_create').hide();

	var nSubId = $J('input[name=w_subid]').val();
	if( !nSubId )
		nSubId = $J('input:radio[name=w_rsubid]:checked').val();

	var strDesc = $J('textarea[name=w_text]').val();

	var strWidgetURL;
	if( nSubId )
		strWidgetURL = 'http://store.steampowered.com/widget/' + nAppId + '/' + nSubId + '/';
	else
		strWidgetURL = 'http://store.steampowered.com/widget/' + nAppId + '/';

	if( strDesc )
		strWidgetURL = strWidgetURL + '?t=' + encodeURIComponent( strDesc );

	var strWidgetCode = '<iframe src="' + strWidgetURL + '" frameborder="0" width="646" height="190"></iframe>';

	var $iframe = $J(strWidgetCode);

	$J('#widget_container').empty();
	$J('#widget_container').append($iframe);
	$J('#widget_code').val( strWidgetCode );

	$J('#widget_finished').show();
}

function InitQueueControls( store_appid, steamworks_appid, next_in_queue_appid, snr )
{
	var $FollowBtn = $J('.queue_control_button.queue_btn_follow .queue_btn_inactive');
	var $UnFollowBtn = $J('.queue_control_button.queue_btn_follow .queue_btn_active');
	var $IgnoreBtn = $J('.queue_control_button.queue_btn_ignore .queue_btn_inactive');
	var $UnIgnoreBtn = $J('.queue_control_button.queue_btn_ignore .queue_btn_active');

	$IgnoreBtn.click( function() {
		$J.post( 'http://store.steampowered.com/recommended/ignorerecommendation/', {
			sessionid: g_sessionID,
			appid: store_appid,
			snr: snr
		}).done( function( data ) {
			$IgnoreBtn.hide();
			$UnIgnoreBtn.show();
			GDynamicStore.InvalidateCache();
			if ( data && data.nSaleTaskCompleted ) { NewStickerPackModal( 'Σημειώστε κάτι ως μη ενδιαφέρον' ); } // SummerSale2017
		}).fail( function( jqXHR ) {
			ShowAlertDialog( 'Δεν ενδιαφέρομαι', 'Υπήρξε ένα πρόβλημα με την αποθήκευση των αλλαγών σας. Παρακαλούμε δοκιμάστε ξανά αργότερα.' );
		});
	});

	$UnIgnoreBtn.click( function() {
		$J.post( 'http://store.steampowered.com/recommended/ignorerecommendation/', {
			sessionid: g_sessionID,
			appid: store_appid,
			snr: snr,
			remove: 1
		}).done( function() {
			$IgnoreBtn.show();
			$UnIgnoreBtn.hide();
			GDynamicStore.InvalidateCache();
		}).fail( function() {
			ShowAlertDialog( 'Δεν ενδιαφέρομαι', 'Υπήρξε ένα πρόβλημα με την αποθήκευση των αλλαγών σας. Παρακαλούμε δοκιμάστε ξανά αργότερα.' );
		});
	});

	$FollowBtn.click( function() {
		$J.post( 'http://store.steampowered.com/explore/followgame/', {
			sessionid: g_sessionID,
			appid: steamworks_appid
		}).done( function() {
			$FollowBtn.hide();
			$UnFollowBtn.show();
		}).fail( function() {
			ShowAlertDialog( 'Ακολούθηση', 'Υπήρξε ένα πρόβλημα με την αποθήκευση των αλλαγών σας. Παρακαλούμε δοκιμάστε ξανά αργότερα.' );
		});
	});

	$UnFollowBtn.click( function() {
		$J.post( 'http://store.steampowered.com/explore/followgame/', {
			sessionid: g_sessionID,
			appid: steamworks_appid,
			unfollow: 1
		}).done( function() {
			$FollowBtn.show();
			$UnFollowBtn.hide();
		}).fail( function() {
			ShowAlertDialog( 'Ακολούθηση', 'Υπήρξε ένα πρόβλημα με την αποθήκευση των αλλαγών σας. Παρακαλούμε δοκιμάστε ξανά αργότερα.' );
		});
	});

	// discovery queue settings dialog
	var bQueueIsValid = true;
	var $NextInQueueBtn = $J('.btn_next_in_queue_trigger');

	$NextInQueueBtn.click( function() {
		if ( bQueueIsValid )
			$J('#next_in_queue_form').submit();
		else
			window.location = 'http://store.steampowered.com/explore/startnew';
	});

	$J('a.dq_settings_link').click( function() {
		CDiscoveryQueue.ShowCustomizeDialog( function( data ) {
			if ( data && data.queue && ( data.queue.indexOf( store_appid ) == -1 ||
				( next_in_queue_appid && data.queue.indexOf( next_in_queue_appid ) == -1 ) ) )
			{
				ShowConfirmDialog( 'Προσαρμογή της σειράς ανακάλυψής σας', 'Σας έχουμε ετοιμάσει μια νέα σειρά ανακάλυψης εφαρμόζοντας τις προτιμήσεις σας. Τι θέλετε να κάνετε;',
					'Εξερεύνηση νέας σειράς','Παραμονή εδώ'
				).done( function() {
					window.location = 'http://store.steampowered.com/explore/startnew/' + g_eDiscoveryQueueType + '/';
				});
				bQueueIsValid = false;
			}
			else
			{
				bQueueIsValid = true;
			}
		}, g_eDiscoveryQueueType );
	} );
}


function InitAutocollapse()
{
	$J('.game_page_autocollapse').each( function() {
		var content = this;
		var $Content = $J(content);
		$Content.wrap( $J('<div/>', {'class': 'game_page_autocollapse_ctn' } ) );

		var $Container = $Content.parent();

		var $ReadMore = $J('<div/>', {'class': 'game_page_autocollapse_readmore' }).text( 'ΔΙΑΒΑΣΤΕ ΠΕΡΙΣΣΟΤΕΡΑ' );
		var $Fade = $J('<div/>', {'class': 'game_page_autocollapse_fade' } ).append( $ReadMore );
		$Container.append( $Fade );

		var nInterval = 0;
		var nMaxHeight = parseInt( $Content.css('max-height') );
		var bMaxHeightSet = true;

		$Content.on( 'gamepage_autocollapse_expand', function() {
			if ( $Container.hasClass( 'collapsed' ) )
			{
				$Container.removeClass( 'collapsed' );
				$Container.addClass( 'expanded' );

				if ( bMaxHeightSet )
				{
					$Content.animate( {'max-height': content.scrollHeight + 20 + 'px'}, 'fast', null, function() { $Content.css('max-height', 'none' ); } );
				}
				window.clearInterval( nInterval );
			}
		});

		$ReadMore.click( function() { $Content.trigger('gamepage_autocollapse_expand'); } );

		var fnCheckHeight = function ()	{
			if ( content.scrollHeight > nMaxHeight + 30 )
			{
				$Content.css( 'max-height', nMaxHeight + 'px' );
				$Container.addClass( 'collapsed' );
				window.clearInterval( nInterval );
				bMaxHeightSet = true;
			}
			else if ( bMaxHeightSet )
			{
				$Content.css( 'max-height', 'none' );
				bMaxHeightSet = false;
			}
		};

		nInterval = window.setInterval( fnCheckHeight, 250 );
		fnCheckHeight();

	});
}

function RenderMoreLikeThisBlock( rgRecommendedAppIDs )
{
	if ( !rgRecommendedAppIDs || !rgRecommendedAppIDs.length > 0 || !$J('#recommended_block_content').length )
	{
		$J('#recommended_block').hide();
		return;
	}

	var rgRecommendationsToShow = [];
	var nCurScore = 0;
	for ( var i = 0; i < rgRecommendedAppIDs.length; i++ )
	{
		var unAppID = rgRecommendedAppIDs[i];
		if ( GDynamicStore.BIsAppIgnored( unAppID ) )
			continue;
		else if ( GDynamicStore.BIsAppOwned( unAppID ) )
			rgRecommendationsToShow.push( { score: 3 + nCurScore++, appid: unAppID } );
		else
			rgRecommendationsToShow.push( { score: nCurScore++, appid: unAppID } );
	}

	rgRecommendationsToShow.sort( function( a, b ) { return a.score - b.score; } );

	for ( var i = 0; i < rgRecommendationsToShow.length; i++ )
	{
		var unAppID = rgRecommendationsToShow[i].appid;
		var rgItemData = GStoreItemData.rgAppData[ unAppID ];
		if ( !rgItemData )
			continue;

		var params = {'class': 'small_cap',
				'data-ds-appid': unAppID,
				'href': GStoreItemData.GetAppURL( unAppID, 'recommended' )
		};

		var $CapCtn = $J('<a/>', params );
		GStoreItemData.BindHoverEvents( $CapCtn, unAppID, null );

		$CapCtn.append( $J('<img/>', {src: rgItemData.small_capsulev5, 'class': 'small_cap_img' } ) );
		$CapCtn.append( $J('<h4/>').html( rgItemData.name ) );
		$CapCtn.append( $J(rgItemData.discount_block).addClass( 'discount_block_inline') );

		$J('#recommended_block_content').append( $CapCtn );
	}
	GDynamicStore.DecorateDynamicItems( $J('#recommended_block_content') );
	$J('#recommended_block_content').append( $J('<div/>', { style: 'clear: left;' } ) );
	$J('#recommended_block_content').trigger('v_contentschanged');
}

function ShowEULA( elLink )
{
	var win = window.open( elLink.href,'eula','height=584,width=475,resize=yes,scrollbars=yes');
	win.focus();
}


// formerly user_reviews_store.js

var g_recommendationContents = [ 'friend', 'summary', 'recent_short', 'all', 'positive', 'negative', 'recent', 'funny' ];


function OnRecommendationVotedUp( recommendationid )
{
	for ( var i = 0; i < g_recommendationContents.length; ++i )
	{
		$J( '#RecommendationVoteUpBtn' + g_recommendationContents[i] + recommendationid ).addClass( 'btn_active' );
		$J( '#RecommendationVoteDownBtn' + g_recommendationContents[i] + recommendationid ).removeClass( "btn_active" );
		$J( '#RecommendationVoteTagBtn' + g_recommendationContents[i] + recommendationid + '_' + 1 ).removeClass( 'btn_active' );
	}
}

function OnRecommendationVotedDown( recommendationid )
{
	for ( var i = 0; i < g_recommendationContents.length; ++i )
	{
		$J( '#RecommendationVoteUpBtn' + g_recommendationContents[i] + recommendationid ).removeClass( 'btn_active' );
		$J( '#RecommendationVoteDownBtn' + g_recommendationContents[i] + recommendationid ).addClass( "btn_active" );
		$J( '#RecommendationVoteTagBtn' + g_recommendationContents[i] + recommendationid + '_' + 1 ).removeClass( 'btn_active' );
	}
}

function OnRecommendationVotedTag( recommendationid, tagID, bRateUp )
{
	for ( var i = 0; i < g_recommendationContents.length; ++i )
	{
		if ( bRateUp )
		{
			$J( '#RecommendationVoteTagBtn' + g_recommendationContents[i] + recommendationid + '_' + tagID ).addClass( 'btn_active' );
			$J( '#RecommendationVoteUpBtn' + g_recommendationContents[i] + recommendationid ).removeClass( 'btn_active' );
			$J( '#RecommendationVoteDownBtn' + g_recommendationContents[i] + recommendationid ).removeClass( "btn_active" );
		}
		else
		{
			$J( '#RecommendationVoteTagBtn' + g_recommendationContents[i] + recommendationid + '_' + tagID ).removeClass( 'btn_active' );
		}
	}
}

function RequestCurrentUserRecommendationVotes( recommendationIDs )
{
	if ( recommendationIDs.length == 0 )
	{
		return;
	}

	$J.post( 'http://store.steampowered.com//userreviews/ajaxgetvotes/', {
			'recommendationids' : recommendationIDs
		}
	).done( function( response ) {
			if ( response.success == 1 )
			{
				var votes = response.votes;
				for ( var i = 0; i < votes.length; ++i )
				{
					var vote = votes[i];
					if ( vote.voted_up )
					{
						OnRecommendationVotedUp( vote.recommendationid );
					}
					else if ( vote.voted_down )
					{
						OnRecommendationVotedDown( vote.recommendationid );
					}
					if ( vote.voted_funny )
					{
						OnRecommendationVotedTag( vote.recommendationid, 1, true );
					}
				}
			}
		} );
}

function UserReviewVoteUp( id )
{
	UserReview_Rate( id, true, 'http://store.steampowered.com/',
		function( rgResults ) {
			if ( rgResults.nSaleTaskCompleted ) { NewStickerPackModal( 'Σημειώστε μια κριτική ως χρήσιμη... ή και όχι');}
			OnRecommendationVotedUp( id );
		}
	);
}

function UserReviewVoteDown( id )
{
	UserReview_Rate( id, false, 'http://store.steampowered.com/',
		function( rgResults ) {
			if ( rgResults.nSaleTaskCompleted ) { NewStickerPackModal( 'Σημειώστε μια κριτική ως χρήσιμη... ή και όχι');}
			OnRecommendationVotedDown( id );
		}
	);
}

function UserReviewVoteTag( id, tagID, elemID )
{
	var elem = $J( '#' + elemID );
	var bRateUp = !elem.hasClass( 'btn_active' );
	UserReview_VoteTag( id, tagID, bRateUp, 'http://store.steampowered.com/',
		function( rgResults ) {
			OnRecommendationVotedTag( id, tagID, bRateUp );
		}
	);
}

function UserReviewSetQuality( id )
{
	var quality = $J( "#ReviewQuality" + id ).val();
	UserReview_Moderate_SetQuality( id, quality, 'http://store.steampowered.com/',
		function( rgResults ) {
		}
	);
}

function UserReviewShowMore( id, context )
{
	$J('#ReviewContent'+context+id).parent().removeClass('partial');
	$J('#ReviewContent'+context+id).parent().addClass('expanded');
}

function LoadMoreReviews( appid, startOffset, dayRange, startDate, endDate, context )
{
	$J( "#ViewAllReviews" + context ).remove();
	$J( "#LoadMoreReviews" + context ).remove();
	$J( "#LoadingMoreReviews" + context ).show();

	var container = $J( "#Reviews_" + context );

	var reviewType = $J('input[name="review_type"]:checked').val();
	var purchaseType = $J('input[name="purchase_type"]:checked').val();
	var language = $J('input[name="review_language"]:checked').val();
	var reviewBetaEnabled = $J( "#ReviewBetaCheckbox" ).is( ":checked" ) ? 1 : 0;
	var dateRangeType = $J('input[name="review_date_range"]:checked').val();
	var summaryNumPositiveReviews = $J( "#review_summary_num_positive_reviews" ).val();
	var summaryNumReviews = $J( "#review_summary_num_reviews" ).val();

	var filteredReviewScore = $J( "#user_reviews_filter_score" );
	filteredReviewScore.removeClass( "visible" );

	$J.get( 'http://store.steampowered.com/appreviews/' + appid,{
		'start_offset' : startOffset,
		'day_range' : dayRange,
		'start_date' : startDate,
		'end_date' : endDate,
		'date_range_type' : dateRangeType,
		'filter' : context,
		'language' : language,
		'l' : 'greek',
		'review_type' : reviewType,
		'purchase_type' : purchaseType,
		'review_beta_enabled' : reviewBetaEnabled,
		'summary_num_positive_reviews' : summaryNumPositiveReviews,
		'summary_num_reviews' : summaryNumReviews
	}).done( function( data ) {

		RecordAJAXPageView( this.url );

		if ( data.success == 1 )
		{
			$J( "#Reviews_loading" ).hide();
			$J( "#Reviews_" + context ).show();

			if ( startOffset == 0 )
			{
				var filteredReviewScore = $J( "#user_reviews_filter_score" );
				if ( data.review_score )
				{
					filteredReviewScore.addClass( "visible" );
					filteredReviewScore.html( data.review_score );
					BindStoreTooltip( $J('#user_reviews_filter_score [data-store-tooltip]' ) );
				}
				else
				{
					filteredReviewScore.removeClass( "visible" );
				}
			}
			$J( "#LoadingMoreReviews" + context ).remove();

			// remove duplicates
			var recommendationIDs = [];
			var temp = $J('<div></div>');
			temp.append( data.html );
			for ( var i = 0; i < data.recommendationids.length; ++i )
			{
				var recommendationid = data.recommendationids[i];
				var elemID = "#ReviewContent" + context + recommendationid;
				if ( $J( elemID ).length != 0 )
				{
					temp.find( elemID ).parent().remove();
				}
				else
				{
					recommendationIDs.push( recommendationid );
				}
			}

			container.append( temp.children() );

			if ( data.date_filter_text )
			{
				var dateFilterText = $J( "#review_selected_histogram_date_range_text" );
				dateFilterText.text( data.date_filter_text );
				dateFilterText.show();
			}

			// all dupes, request more
			if ( data.recommendationids.length != 0 && recommendationIDs.length == 0 )
			{
				LoadMoreReviews(appid, startOffset + data.recommendationids.length, data.dayrange, data.start_date, data.end_date, context );
			}
			else
			{
				CollapseLongReviews();
				RequestCurrentUserRecommendationVotes( recommendationIDs );
			}
		}
	} );
}

function SelectReviews( appid, context, reviewDayRange, startDate, endDate, forceClear )
{
	$J( "#ReviewsTab_summary" ).removeClass( "active" );
	$J( "#ReviewsTab_all" ).removeClass( "active" );
	$J( "#ReviewsTab_recent" ).removeClass( "active" );
	$J( "#ReviewsTab_positive" ).removeClass( "active" );
	$J( "#ReviewsTab_negative" ).removeClass( "active" );
	$J( "#ReviewsTab_funny" ).removeClass( "active" );
	$J( "#ReviewsTab_" + context ).addClass( "active" );

	$J( "#Reviews_summary" ).hide();
	$J( "#Reviews_all" ).hide();
	$J( "#Reviews_recent" ).hide();
	$J( "#Reviews_positive" ).hide();
	$J( "#Reviews_negative" ).hide();
	$J( "#Reviews_funny" ).hide();
	$J( "#Reviews_loading" ).show();

	var container = $J( "#Reviews_" + context );
	if ( forceClear )
	{
		container.empty()
	}
	if ( container.children().length == 0 )
	{
		LoadMoreReviews( appid, 0, reviewDayRange, startDate, endDate, context );
	}
}

function FilterReviewsToGraph( bCountAllReviews, startDate, endDate )
{
	if ( bCountAllReviews )
	{
		$J('#purchase_type_all').attr( 'checked', true );
	}
	else
	{
		$J('#purchase_type_steam').attr( 'checked', !bCountAllReviews );
	}

	$J('#review_start_date').val( startDate );
	$J('#review_end_date').val( endDate );
	if ( !$J('#review_date_range_histogram').attr( 'checked' ) && !$J('#review_date_range_exclude_histogram').attr( 'checked') )
	{
		$J('#review_date_range_histogram').attr('checked', true);
	}
	$J('#review_date_range_histogram').attr( 'disabled', false );
	$J('#review_date_range_exclude_histogram').attr( 'disabled', false );
	ShowFilteredReviews();
}

function FilterReviewsGraph( bCountAllReviews, startDate, endDate, bExclude )
{
	if ( bCountAllReviews )
	{
		$J('#purchase_type_all').attr( 'checked', true );
	}
	else
	{
		$J('#purchase_type_steam').attr( 'checked', !bCountAllReviews );
	}

	$J('#review_start_date').val( startDate );
	$J('#review_end_date').val( endDate );

	$J('#review_date_range_histogram').attr( 'disabled', false );
	$J('#review_date_range_exclude_histogram').attr( 'disabled', false );

	if ( bExclude )
	{
		$J('#review_date_range_exclude_histogram').attr( 'checked', true );
	}
	else
	{
		$J('#review_date_range_histogram').attr( 'checked', true );
	}

	ShowFilteredReviews();
}

function ClearReviewTypeFilter()
{
	$J('#review_type_all').attr( 'checked', true );
	ShowFilteredReviews();
}

function ClearReviewPurchaseTypeFilter()
{
	$J('#purchase_type_all').attr( 'checked', true );
	ShowFilteredReviews();
}

function ClearReviewLanguageFilter()
{
	$J('#review_language_all').attr( 'checked', true );
	ShowFilteredReviews();
}

function ClearReviewDateRangeFilter()
{
	$J('#review_date_range_all').attr( 'checked', true );
	ClearReviewDateFilter();
}

function BuildReviewHistogram()
{
	if( $J( "#review_histograms_container" ).length == 0 )
		return;

	var appid = $J( "#review_appid" ).val();

	$J.get( 'http://store.steampowered.com/appreviewhistogram/' + appid, { l: 'greek' }
	).done( function( data ) {

		$J( "#review_histograms_container" ).addClass( "has_data" );

		// remove so we can draw to the canvas
		$J( "#review_histograms_container" ).removeClass( "collapsed" );
		$J( "#reviews_filter_options" ).removeClass( "graph_collapsed" );

		var bCountAllReviews = data.count_all_reviews;
		// language
		var elemLanguageBreakdown = $J( "#review_language_breakdown" );

		var numTotalDays = ( data.results.end_date - data.results.start_date ) / 86400;
		var numReviewsRecent = 0;
		for ( var i = 0; i < data.results.recent.length; ++i )
		{
			var recentDay = data.results.recent[i];
			numReviewsRecent += recentDay.recommendations_up + recentDay.recommendations_down;
		}

		if ( numTotalDays < 7 )
		{
			$J( "#app_reviews_hash" ).addClass( "graph_hidden" );
			$J( "#review_histogram_rollup_container" ).hide();
			$J( "#review_histogram_rollup_section" ).addClass( "recent" );
			$J( "#review_histogram_rollup_section" ).addClass( "hidden" );
			$J( "#review_histogram_recent_section" ).hide();
			return;
		}
		else if ( numReviewsRecent == 0 )
		{
			data.results.recent = null;
			$J( "#review_histogram_rollup_section" ).addClass( "recent" );
			$J( "#review_histogram_recent_section" ).hide();
		}
		else if ( numTotalDays < 30 )
		{
			$J( "#review_histogram_rollup_section" ).addClass( "recent" );
			$J( "#review_histogram_recent_section" ).hide();

			data.results.rollups = data.results.recent;
			data.results.recent = null;
			data.results.rollup_type = 'day';
		}

		var chartDataPositive = [];
		var chartDataNegative = [];

		for ( var i = 0; i < data.results.rollups.length; ++i )
		{
			var rollup = data.results.rollups[i];
			var barDataUp = [ rollup.date * 1000, rollup.recommendations_up ];
			var barDataDown = [ rollup.date * 1000, -rollup.recommendations_down ];
			chartDataPositive.push( barDataUp );
			chartDataNegative.push( barDataDown );
		}
		var seriesRollup = [ { label: "Θετικές", color: "#66c0f4", fillColor: "#66c0f4", data: chartDataPositive }, { label: "Αρνητικές", color: "#A34C25", fillColor: "#A34C25", data: chartDataNegative } ];

		var seriesRecent = null;
		if ( data.results.recent )
		{
			chartDataPositive = [];
			chartDataNegative = [];
			for ( var i = 0; i < data.results.recent.length; ++i )
			{
				var recentDay = data.results.recent[i];
				var barDataUp = [ recentDay.date * 1000, recentDay.recommendations_up ];
				var barDataDown = [ recentDay.date * 1000, -recentDay.recommendations_down ];
				chartDataPositive.push( barDataUp );
				chartDataNegative.push( barDataDown );
			}
			seriesRecent = [ { color: "#66c0f4", label: "Θετικές", data: chartDataPositive }, { color: "#A34C25", label: "Αρνητικές", data: chartDataNegative } ];
		}

		var options = {
			series: {
				stack: 0,
				bars: {
					show: 1,
					fill: 1,
					lineWidth: 0,
					barWidth: 86400*1000 * 0.5,
					align: "left"
				},
				lines: {
					show: 0
				},
				highlightColor: 'rgb(255,255,255)'
			},
			legend: {
				show: 0
			},
			xaxis: {
				mode: "time",
				timeformat: "%b %d",
				timezone: "utc",
				tickLength: 0
			},
			yaxis: {
				tickFormatter : function( val, axis ) {
					return ( val < 0 ) ? -val : val;
				},
				tickLength: 0,
				tickDecimals: 0
			},
			grid: {
				hoverable: true,
				clickable: true,
				borderWidth: 0,
				margin: 0,
				mouseActiveRadius: 10,
				autoHighlight: true,
				markings: [ { yaxis: { from: 0, to: 0 }, color: "#4582A5" } ]
			},
			selection: {
				mode: "x",
				color: "#ffffff",
			}
		};

		// week/month rollup
		var rollupOptions = $J.extend( true, {}, options );
		if ( data.results.rollup_type == 'week' )
		{
			if ( numTotalDays > 365 / 2 )
			{
				rollupOptions.xaxis.timeformat = "%b";
			}
			else
			{
				rollupOptions.xaxis.timeformat = "%b %d";
			}
			rollupOptions.series.bars.barWidth = 86400*1000 * 7 * 0.5;
		}
		else if ( data.results.rollup_type == 'month' )
		{
			rollupOptions.xaxis.timeformat = numTotalDays > 365 ? "%b %Y": "%b";
			rollupOptions.series.bars.barWidth = 86400*1000 * 30 * 0.5;
		}
		var graphRollup =  $J( "#review_histogram_rollup" );
		var flotRollup = $J.plot( graphRollup, seriesRollup, rollupOptions );

		// recent
		var graphRecent = null;
		if ( seriesRecent )
		{
			var recentOptions = $J.extend( true, {}, options );
			recentOptions.series.bars.barWidth = 86400*1000 * 0.5;
			recentOptions.xaxis.timeformat = "%b %d";
			var graphRecent =  $J( "#review_histogram_recent" );
			var flotRecent = $J.plot( graphRecent, seriesRecent, recentOptions );

			var recentGraphStartDate = data.results.recent[0].date * 1000;

			var rollupPoints = flotRollup.getData();
			var seriesPositive = rollupPoints[0];
			{
				var startX = seriesPositive.xaxis.p2c( recentGraphStartDate );
				var endPoint = seriesPositive.data[seriesPositive.data.length-1];
				var endX = seriesPositive.xaxis.p2c( endPoint[0] + rollupOptions.series.bars.barWidth );
				var highlightWidth = endX - startX;

				// get min/max of the yaxis
				var axes = flotRollup.getAxes();
				var startY = seriesPositive.yaxis.p2c( axes.yaxis.max );
				var endY = seriesPositive.yaxis.p2c( axes.yaxis.min );
				var highlightHeight = endY - startY;

				var funcDrawGraphOverlay = function() {

					var c = document.getElementById("review_graph_canvas");
					var ctx = c.getContext("2d");
					// resize the canvas to the same size as the element, so our drawing doesn't look blurry
					c.width = $J(c).width();
					c.height = $J(c).height();
					ctx.clearRect(0, 0, c.width, c.height);
					// these should be 1-to-1 now, but for correctness, we need to
					var scaleX = c.width / c.offsetWidth;
					var scaleY = c.height / c.offsetHeight;

					// draw rect on the graph
					ctx.fillStyle = 'rgba(148,217,255,0.2)';
					var offsets = flotRollup.getPlotOffset();
					var offsetLeft = ( offsets.left + startX ) + $J(flotRollup.getCanvas()).offsetParent().position().left;
					var offsetTop = ( offsets.top + startY ) + $J(flotRollup.getCanvas()).offsetParent().position().top;
					ctx.fillRect(offsetLeft * scaleX, offsetTop * scaleY, highlightWidth, highlightHeight);

					// now draw the "pop-out" on our other canvas
					offsetLeft = ( offsets.left + endX ) + $J(flotRollup.getCanvas()).offsetParent().position().left;
					offsetTop = ( offsets.top + startY ) + $J(flotRollup.getCanvas()).offsetParent().position().top;
					var offsetBottom = ( offsets.top + endY ) + $J(flotRollup.getCanvas()).offsetParent().position().top;
					var recentSection = $J("#review_histogram_recent_section");

					ctx.fillStyle = 'rgba(33,44,61,1)';
					ctx.beginPath();
					ctx.moveTo(offsetLeft * scaleX, offsetTop * scaleY);
					ctx.lineTo(recentSection.position().left * scaleX, recentSection.position().top * scaleY);
					ctx.lineTo(recentSection.position().left * scaleX, ( recentSection.position().top + recentSection.height() ) * scaleY);
					ctx.lineTo(offsetLeft * scaleX, offsetBottom * scaleY);
					ctx.lineTo(offsetLeft * scaleX, offsetTop * scaleY);
					ctx.fill();
				};

				$J("#review_graph_canvas").resize( funcDrawGraphOverlay );
				funcDrawGraphOverlay();
			}
		}

		// tooltip
		$J("<div id='review_histogram_tooltip'></div>").appendTo("body");
		var funcTooltip = function (event, pos, item) {
			var tooltip = $J("#review_histogram_tooltip");
			if ( item )
			{
				var x = item.datapoint[0].toFixed(2);
				var y = item.datapoint[1].toFixed(2);
				var numReviews = parseInt( y );
				var bNegativeReviews = numReviews < 0;
				var yDelta = bNegativeReviews ? 10 : ( -20 - tooltip.height() );
				var xDelta = 5;
				numReviews = Math.abs( numReviews );

				var date = new Date( parseInt(x) );

				var strDate = ( date.getUTCMonth() + 1 ) + "/" + ( date.getUTCDate() ) + "/" + date.getUTCFullYear();
				tooltip.html( numReviews + " " + item.series.label + " (" + strDate + ")" );
				tooltip.css( {top: item.pageY+yDelta, left: item.pageX+xDelta} );
				tooltip.fadeIn( 10 );

				tooltip.toggleClass( "negative", bNegativeReviews );
			}
			else
			{
				tooltip.hide();
			}
		};

		// click
		var funcClick = function( plot, rollupType ) {
			return function (event, pos, item) {
				// ignore for selection
				if ( plot.getSelection() )
				{
					return;
				}

				if ( item )
				{
					var x = item.datapoint[0].toFixed(2);
					var y = item.datapoint[1].toFixed(2);
					var numReviews = parseInt( y );
					var bNegativeReviews = numReviews < 0;

					var date = new Date( parseInt(x) );
					var startDate = date.getTime() / 1000;
					var endDate = startDate + 86400;
					if ( rollupType == 'week' )
					{
						endDate = startDate + 86400 * 7;
					}
					else if ( rollupType == 'month' )
					{
						var lastDayOfMonth = new Date( date.getUTCFullYear(), date.getUTCMonth() + 1, 0 );
						endDate = lastDayOfMonth.getTime() / 1000;
					}

					$J( bNegativeReviews ? '#review_type_negative' : '#review_type_positive').attr( 'checked', true );
					FilterReviewsToGraph( bCountAllReviews, startDate, endDate );

					flotRollup.clearSelection();
					if ( flotRecent )
					{
						flotRecent.clearSelection();
					}
				}
			}
		};

		var funcSelected = function( plot ) {
			return function (event, ranges) {

				var startDate = ranges.xaxis.from.toFixed(1) / 1000;
				var endDate = ranges.xaxis.to.toFixed(1) / 1000;

				$J( '#review_type_all' ).attr( 'checked', true );
				FilterReviewsToGraph( bCountAllReviews, startDate, endDate );
				if ( plot == flotRollup && flotRecent )
				{
					flotRecent.clearSelection();
				}
				else if ( plot == flotRecent )
				{
					flotRollup.clearSelection();
				}
			};
		};

		var funcUnSelected = function ( event ) {
			
		};

		graphRollup.bind("plothover", funcTooltip);
		graphRollup.bind("plotclick", funcClick( flotRollup, data.results.rollup_type ) );
		graphRollup.bind("plotselected", funcSelected( flotRollup ));
		graphRollup.bind("plotunselected", funcUnSelected );
		if ( graphRecent )
		{
			graphRecent.bind("plothover", funcTooltip);
			graphRecent.bind("plotclick", funcClick( flotRecent, 'day' ) );
			graphRecent.bind("plotselected", funcSelected( flotRecent ) );
			graphRecent.bind("plotunselected", funcUnSelected );
		}

		// recent events
		if ( data.results.recent_events && data.results.recent_events.length > 0 )
		{
			var event = data.results.recent_events[0];
			var container = $J( "#review_recent_events_container" );
			container.addClass( event.type );
			$J( "#recent_review_event_title" ).text( data.results.recent_events_title );
			$J( "#recent_review_event_dates" ).text( data.results.recent_event_dates );
			$J( "#recent_review_event_text" ).text( data.results.recent_events_text );
			$J( "#filter_reviews_to_event_btn" ).click( function() {
				$J( '#review_type_all' ).attr( 'checked', true );
				FilterReviewsGraph( bCountAllReviews, event.start_date, event.end_date, false );
				if ( flotRecent )
				{
					flotRecent.clearSelection();
				}
				flotRollup.clearSelection();
			});
			$J( "#filter_reviews_exclude_event_btn" ).click( function() {
				$J( '#review_type_all' ).attr( 'checked', true );
				FilterReviewsGraph( bCountAllReviews, event.start_date, event.end_date, true );
				if ( flotRecent )
				{
					flotRecent.clearSelection();
				}
				flotRollup.clearSelection();
			});
			container.show();
		}
		else
		{
			$J( "#review_histograms_container" ).addClass( "collapsed" );
			$J( "#reviews_filter_options" ).addClass( "graph_collapsed" );
		}

	} );
}

function SetReviewsGraphVisibility( bVisible )
{
	$J( "#review_histograms_container" ).toggleClass( "collapsed", !bVisible );
	$J( "#reviews_filter_options" ).toggleClass( "graph_collapsed", !bVisible );
}

function ClearReviewDateFilter()
{
	$J('#review_start_date').val( -1 );
	$J('#review_end_date').val( -1 );
	$J('#review_date_range_histogram').attr( 'disabled', true );
	$J('#review_date_range_exclude_histogram').attr( 'disabled', true );
	$J( "#review_selected_histogram_date_range_text" ).hide();
	ShowFilteredReviews();
}

function OnLoadReviews()
{
	BuildReviewHistogram();
	ShowFilteredReviews();
}

function UpdateActiveFilters()
{
	var bAnyActiveFilters = false;
	// type
	if ( $J( "#review_type_positive" ).attr( "checked" ) )
	{
		bAnyActiveFilters = true;
		$J( "#reviews_filter_type" ).show();
		$J( "#reviews_filter_type" ).text( 'Θετικές' );
	}
	else if ( $J( "#review_type_negative" ).attr( "checked" ) )
	{
		bAnyActiveFilters = true;
		$J( "#reviews_filter_type" ).show();
		$J( "#reviews_filter_type" ).text( 'Αρνητικές' );
	}
	else
	{
		$J( "#reviews_filter_type" ).hide();
	}

	// purchase type
	if ( $J( "#purchase_type_steam" ).attr( "checked" ) )
	{
		bAnyActiveFilters = true;
		$J( "#reviews_filter_purchase_type" ).show();
		$J( "#reviews_filter_purchase_type" ).text( 'Αγορά από το Steam' );
	}
	else if ( $J( "#purchase_type_non_steam" ).attr( "checked" ) )
	{
		bAnyActiveFilters = true;
		$J( "#reviews_filter_purchase_type" ).show();
		$J( "#reviews_filter_purchase_type" ).text( 'Δεν αγοράστηκε στο Steam' );
	}
	else
	{
		$J( "#reviews_filter_purchase_type" ).hide();
	}

	// language
	if ( $J( "#review_language_mine" ).attr( "checked" ) )
	{
		bAnyActiveFilters = true;
		$J( "#reviews_filter_language" ).show();
		$J( "#reviews_filter_language" ).text( 'Οι γλώσσες σας' );
	}
	else
	{
		$J( "#reviews_filter_language" ).hide();
	}

	// graph
	if ( $J( "#review_date_range_histogram" ).attr( "checked" ) )
	{
		bAnyActiveFilters = true;
		$J( "#reviews_filter_graph" ).show();
		$J( "#review_selected_histogram_date_range_prefix" ).text( 'Προβολή μόνο ' );
	}
	else if ( $J( "#review_date_range_exclude_histogram" ).attr( "checked" ) )
	{
		bAnyActiveFilters = true;
		$J( "#reviews_filter_graph" ).show();
		$J( "#review_selected_histogram_date_range_prefix" ).text( 'Απόκρυψη ' );
	}
	else
	{
		$J( "#reviews_filter_graph" ).hide();
	}
	
	// beta
	var context = $J( "#review_context" ).val();
	if ( $J( "#ReviewBetaCheckbox" ).attr( "checked" ) && ( context == 'summary' || context == 'all' ) )
	{
		bAnyActiveFilters = true;
		$J( "#reviews_beta" ).show();
	}
	else
	{
		$J( "#reviews_beta" ).hide();
	}

	$J( "#reviews_filter_title" ).toggle( bAnyActiveFilters );
}

function ShowFilteredReviews()
{
	UpdateActiveFilters();

	var appid = $J( "#review_appid" ).val();
	var context = $J( "#review_context" ).val();
	var defaultDayRange = $J( "#review_default_day_range" ).val();
	var startDate = $J( "#review_start_date" ).val();
	var endDate = $J( "#review_end_date" ).val();
	SelectReviews( appid, context, defaultDayRange, startDate, endDate, true );
}

function ChangeReviewPurchaseTypeFilter()
{
	var purchaseType = $J('input[name="purchase_type"]:checked').val();
	V_SetCookie( "review_purchase_type_filter", purchaseType, purchaseType == 'all' ? 1 : 14, "app/" );
	ShowFilteredReviews();
}

function ChangedReviewHelpfulnessFilter()
{
	var bReviewBetaEnabled = $J( "#ReviewBetaCheckbox" ).is( ":checked" ) ? 1 : 0;
	V_SetCookie( "review_beta_enabled", bReviewBetaEnabled, 7, "app/" );
	ShowFilteredReviews();
}

function ClearReviewBetaFilter()
{
	$J('#ReviewBetaCheckbox').attr( 'checked', false );
	var bReviewBetaEnabled = false;
	V_SetCookie( "review_beta_enabled", bReviewBetaEnabled, 7, "app/" );
	ShowFilteredReviews();
}

function CollapseLongReviews()
{
	$J('.review_box').each( function(j, i){
		if( $J(i).outerHeight() > 400 )
		{
			if ( !$J(i).hasClass('expanded') )
			{
				$J(i).addClass('partial')
			}
		}
	});
}

//formerly app_reporting.js

var gReportedApp = false;
function ShowReportDialog( nAppId )
{
	if ( gReportedApp )
	{
		return;
	}

	var content = $J('<div/>', {'class': 'app_report_dialog' } );

	content.append( $J('<div/>', {'class': 'app_report_dialog_intro' } ).text('Επιλέξτε μια αιτία για το λόγο που αναφέρετε αυτό το προϊόν.') );

		var rgReportOptions = {"1":"\u0391\u03c0\u03ac\u03c4\u03b7 - <span class=\"sub\">\u0391\u03c5\u03c4\u03cc \u03c4\u03bf \u03bb\u03bf\u03b3\u03b9\u03c3\u03bc\u03b9\u03ba\u03cc \u03c0\u03c1\u03bf\u03c3\u03c0\u03b1\u03b8\u03b5\u03af \u03bc\u03b5 \u03b4\u03cc\u03bb\u03bf \u03bd\u03b1 \u03c3\u03c5\u03bb\u03bb\u03ad\u03be\u03b5\u03b9 \u03b5\u03c5\u03b1\u03af\u03c3\u03b8\u03b7\u03c4\u03b5\u03c2 \u03c0\u03bb\u03b7\u03c1\u03bf\u03c6\u03bf\u03c1\u03af\u03b5\u03c2, \u03cc\u03c0\u03c9\u03c2 \u03c4\u03b1 \u03b4\u03b9\u03b1\u03c0\u03b9\u03c3\u03c4\u03b5\u03c5\u03c4\u03ae\u03c1\u03b9\u03b1 Steam \u03c3\u03b1\u03c2 \u03ae \u03bf\u03b9\u03ba\u03bf\u03bd\u03bf\u03bc\u03b9\u03ba\u03ac \u03c3\u03c4\u03bf\u03b9\u03c7\u03b5\u03af\u03b1 (\u03c0.\u03c7. \u03c0\u03bb\u03b7\u03c1\u03bf\u03c6\u03bf\u03c1\u03af\u03b5\u03c2 \u03c0\u03b9\u03c3\u03c4\u03c9\u03c4\u03b9\u03ba\u03ae\u03c2 \u03ba\u03ac\u03c1\u03c4\u03b1\u03c2).<\/span>","2":"\u0395\u03c0\u03b9\u03b2\u03bb\u03b1\u03b2\u03ad\u03c2 - <span class=\"sub\">\u03a4\u03bf \u03bb\u03bf\u03b3\u03b9\u03c3\u03bc\u03b9\u03ba\u03cc \u03b1\u03c5\u03c4\u03cc \u03c4\u03c1\u03bf\u03c0\u03bf\u03c0\u03bf\u03b9\u03b5\u03af \u03c4\u03bf\u03bd \u03c5\u03c0\u03bf\u03bb\u03bf\u03b3\u03b9\u03c3\u03c4\u03ae \u03c4\u03bf\u03c5 \u03c0\u03b5\u03bb\u03ac\u03c4\u03b7 \u03bc\u03b5 \u03b1\u03c0\u03c1\u03bf\u03c3\u03b4\u03cc\u03ba\u03b7\u03c4\u03bf \u03ae \u03b5\u03c0\u03b9\u03b2\u03bb\u03b1\u03b2\u03ae \u03c4\u03c1\u03cc\u03c0\u03bf (\u03c0.\u03c7. \u03b5\u03af\u03bd\u03b1\u03b9 malware \u03ae \u03b9\u03cc\u03c2)<\/span>","3":"\u03a1\u03b7\u03c4\u03bf\u03c1\u03b9\u03ba\u03ae \u03bc\u03af\u03c3\u03bf\u03c5\u03c2 - <span class=\"sub\">\u03a0\u03b5\u03c1\u03b9\u03ad\u03c7\u03b5\u03b9 \u03c1\u03b7\u03c4\u03bf\u03c1\u03b9\u03ba\u03ae \u03bc\u03af\u03c3\u03bf\u03c5\u03c2, \u03b4\u03b7\u03bb. \u03bf\u03bc\u03b9\u03bb\u03af\u03b1 \u03c0\u03bf\u03c5 \u03c0\u03c1\u03bf\u03c9\u03b8\u03b5\u03af \u03c4\u03bf \u03bc\u03af\u03c3\u03bf\u03c2, \u03c4\u03b7 \u03b2\u03af\u03b1 \u03ae \u03c4\u03b7 \u03b4\u03b9\u03ac\u03ba\u03c1\u03b9\u03c3\u03b7 \u03bf\u03bc\u03ac\u03b4\u03c9\u03bd \u03ae \u03b1\u03c4\u03cc\u03bc\u03c9\u03bd \u03bc\u03b5 \u03b2\u03ac\u03c3\u03b7 \u03c4\u03b7\u03bd \u03b5\u03b8\u03bd\u03b9\u03ba\u03cc\u03c4\u03b7\u03c4\u03b1, \u03c4\u03b7 \u03b8\u03c1\u03b7\u03c3\u03ba\u03b5\u03af\u03b1, \u03c4\u03bf \u03c6\u03cd\u03bb\u03bf, \u03c4\u03b7\u03bd \u03b7\u03bb\u03b9\u03ba\u03af\u03b1, \u03c4\u03b7\u03bd \u03b1\u03bd\u03b1\u03c0\u03b7\u03c1\u03af\u03b1 \u03ae \u03c4\u03b9\u03c2 \u03c3\u03b5\u03be\u03bf\u03c5\u03b1\u03bb\u03b9\u03ba\u03ad\u03c2 \u03c4\u03bf\u03c5\u03c2 \u03c0\u03c1\u03bf\u03c4\u03b9\u03bc\u03ae\u03c3\u03b5\u03b9\u03c2.<\/span>","4":"\u03a0\u03bf\u03c1\u03bd\u03bf\u03b3\u03c1\u03b1\u03c6\u03af\u03b1 - <span class=\"sub\">\u03a0\u03b5\u03c1\u03b9\u03ad\u03c7\u03b5\u03b9 \u03c0\u03bf\u03c1\u03bd\u03bf\u03b3\u03c1\u03b1\u03c6\u03af\u03b1<\/span>","5":"\u03a0\u03b5\u03c1\u03b9\u03b5\u03c7\u03cc\u03bc\u03b5\u03bd\u03bf \u03b3\u03b9\u03b1 \u03b5\u03bd\u03b7\u03bb\u03af\u03ba\u03bf\u03c5\u03c2 - <span class=\"sub\">\u03a0\u03b5\u03c1\u03b9\u03ad\u03c7\u03b5\u03b9 \u03c0\u03b5\u03c1\u03b9\u03b5\u03c7\u03cc\u03bc\u03b5\u03bd\u03bf \u03b3\u03b9\u03b1 \u03b5\u03bd\u03b7\u03bb\u03af\u03ba\u03bf\u03c5\u03c2 \u03c0\u03bf\u03c5 \u03b4\u03b5\u03bd \u03ad\u03c7\u03b5\u03b9 \u03c3\u03b7\u03bc\u03b1\u03bd\u03b8\u03b5\u03af \u03ba\u03b1\u03c4\u03ac\u03bb\u03bb\u03b7\u03bb\u03b1 \u03ba\u03b1\u03b9 \u03b4\u03b5\u03bd \u03ad\u03c7\u03b5\u03b9 \u03c0\u03b5\u03c1\u03b9\u03bf\u03c1\u03b9\u03c3\u03bc\u03cc \u03b7\u03bb\u03b9\u03ba\u03af\u03b1\u03c2<\/span>","6":"\u0394\u03c5\u03c3\u03c6\u03b7\u03bc\u03b9\u03c3\u03c4\u03b9\u03ba\u03cc - <span class=\"sub\">\u03a0\u03b5\u03c1\u03b9\u03ad\u03c7\u03b5\u03b9 \u03c3\u03c5\u03ba\u03bf\u03c6\u03b1\u03bd\u03c4\u03b9\u03ba\u03ad\u03c2 \u03ae \u03b4\u03c5\u03c3\u03c6\u03b7\u03bc\u03b9\u03c3\u03c4\u03b9\u03ba\u03ad\u03c2 \u03b4\u03b7\u03bb\u03ce\u03c3\u03b5\u03b9\u03c2<\/span>","7":"\u03a0\u03c1\u03bf\u03c3\u03b2\u03bb\u03b7\u03c4\u03b9\u03ba\u03cc - <span class=\"sub\">\u03a0\u03b5\u03c1\u03b9\u03ad\u03c7\u03b5\u03b9 \u03c0\u03b5\u03c1\u03b9\u03b5\u03c7\u03cc\u03bc\u03b5\u03bd\u03bf \u03c0\u03bf\u03c5 \u03b5\u03af\u03bd\u03b1\u03b9 \u03ba\u03b1\u03b8\u03b1\u03c1\u03ac \u03c0\u03c1\u03bf\u03c3\u03b2\u03bb\u03b7\u03c4\u03b9\u03ba\u03cc \u03ae \u03c3\u03c4\u03bf\u03c7\u03b5\u03cd\u03b5\u03b9 \u03bd\u03b1 \u03c3\u03bf\u03ba\u03ac\u03c1\u03b5\u03b9 \u03ae \u03bd\u03b1 \u03b1\u03b7\u03b4\u03b9\u03ac\u03c3\u03b5\u03b9 \u03c4\u03bf\u03c5\u03c2 \u03b8\u03b5\u03b1\u03c4\u03ad\u03c2<\/span>","8":"\u03a0\u03b1\u03b9\u03b4\u03b9\u03ba\u03ae \u03b5\u03ba\u03bc\u03b5\u03c4\u03ac\u03bb\u03bb\u03b5\u03c5\u03c3\u03b7 - <span class=\"sub\">\u03a0\u03b5\u03c1\u03b9\u03ad\u03c7\u03b5\u03b9 \u03c0\u03b5\u03c1\u03b9\u03b5\u03c7\u03cc\u03bc\u03b5\u03bd\u03bf \u03c0\u03bf\u03c5 \u03b5\u03ba\u03bc\u03b5\u03c4\u03b1\u03bb\u03bb\u03b5\u03cd\u03b5\u03c4\u03b1\u03b9 \u03c4\u03b1 \u03c0\u03b1\u03b9\u03b4\u03b9\u03ac \u03bc\u03b5 \u03bf\u03c0\u03bf\u03b9\u03bf\u03bd\u03b4\u03ae\u03c0\u03bf\u03c4\u03b5 \u03c4\u03c1\u03cc\u03c0\u03bf<\/span>","11":"\u039d\u03bf\u03bc\u03b9\u03ba\u03ae \u03c0\u03b1\u03c1\u03b1\u03b2\u03af\u03b1\u03c3\u03b7 - <span class=\"sub\">\u03a0\u03b5\u03c1\u03b9\u03ad\u03c7\u03b5\u03b9 \u03c0\u03b5\u03c1\u03b9\u03b5\u03c7\u03cc\u03bc\u03b5\u03bd\u03bf \u03c0\u03bf\u03c5 \u03c0\u03b1\u03c1\u03b1\u03b2\u03b9\u03ac\u03b6\u03b5\u03b9 \u03c4\u03bf\u03c5\u03c2 \u03bd\u03cc\u03bc\u03bf\u03c5\u03c2 \u03c4\u03b7\u03c2 \u03b4\u03b9\u03ba\u03b1\u03b9\u03bf\u03b4\u03bf\u03c3\u03af\u03b1\u03c2 \u03c3\u03b1\u03c2<\/span>","13":"\u03a7\u03b1\u03bb\u03b1\u03c3\u03bc\u03ad\u03bd\u03bf - <span class=\"sub\">\u0394\u03b5\u03bd \u03bc\u03c0\u03bf\u03c1\u03b5\u03af \u03bd\u03b1 \u03bb\u03b7\u03c6\u03b8\u03b5\u03af, \u03bd\u03b1 \u03b5\u03ba\u03c4\u03b5\u03bb\u03b5\u03c3\u03c4\u03b5\u03af \u03ae \u03bd\u03b1 \u03bb\u03b5\u03b9\u03c4\u03bf\u03c5\u03c1\u03b3\u03ae\u03c3\u03b5\u03b9 \u03c3\u03c9\u03c3\u03c4\u03ac, \u03b1\u03ba\u03cc\u03bc\u03b1 \u03ba\u03b1\u03b9 \u03c3\u03b5 \u03bc\u03b7\u03c7\u03ac\u03bd\u03b7\u03bc\u03b1 \u03c0\u03bf\u03c5 \u03bd\u03b1 \u03c0\u03bb\u03b7\u03c1\u03bf\u03af \u03c4\u03b9\u03c2 \u03b5\u03bb\u03ac\u03c7\u03b9\u03c3\u03c4\u03b5\u03c2 \u03b1\u03c0\u03b1\u03b9\u03c4\u03ae\u03c3\u03b5\u03b9\u03c2 \u03c3\u03c5\u03c3\u03c4\u03ae\u03bc\u03b1\u03c4\u03bf\u03c2.<\/span>"};
	var rgReportOptionElements = [];
	for ( var eReportType in rgReportOptions )
	{
		var $ReportOption = $J('<div/>', {'class': 'app_report_dialog_option' } );
		$ReportOption.append( $J('<div/>', {'class': 'app_report_dialog_option_input' }).append( $J('<input/>', {'type': 'radio', 'name':'report_type', 'value': eReportType, 'id': 'report_type_' + eReportType } ) ) );
		$ReportOption.append( $J('<div/>', {'class': 'app_report_dialog_option_text' }).append( $J('<label/>', {'for': 'report_type_' + eReportType }).html( rgReportOptions[eReportType] ) ) );
		rgReportOptionElements.push( $ReportOption );
	}

	for ( var j, x, i = rgReportOptionElements.length; i; j = parseInt(Math.random() * i), x = rgReportOptionElements[--i], rgReportOptionElements[i] = rgReportOptionElements[j], rgReportOptionElements[j] = x );
	for ( var i = 0; i < rgReportOptionElements.length; ++i )
	{
		content.append( rgReportOptionElements[i] );
	}

	var textArea = $J('<textarea/>', { 'class': 'app_report_dialog_reason', 'id' : 'ReportReason',  'name' : 'report_reason' } );
	var maxReasonLength = 512;
	textArea.attr( 'maxlength', maxReasonLength );
	textArea.bind( "keyup change",
		function()
		{
			var str = $J(this).val()
			var mx = parseInt($J(this).attr('maxlength'))
			if (str.length > mx)
			{
				$J(this).val(str.substr(0, mx))
				return false;
			}
		}
	);
	content.append( $J('<div/>', {'class': 'app_report_dialog_intro' } ).text('Μπορείτε να εισάγετε επιπλέον πληροφορίες που θεωρείτε ότι είναι σχετικές εδώ:') );
	content.append( textArea );

	content.append( $J('<div/>', {'class': 'app_report_dialog_dmca' } ).html('Εάν θα θέλατε να αναφέρετε παραβίαση πνευματικών δικαιωμάτων και είστε ο κάτοχος των πνευματικών δικαιωμάτων, προχωρήστε στη συμβατή με το DMCA υποβολή ειδοποίησης παραβίασης πνευματικών δικαιωμάτων <a href="http://steamcommunity.com/dmca/create/">εδώ</a>.') );

	var dialog = ShowConfirmDialog( 'Αναφορά προϊόντος', content, 'Αναφορά');

	dialog.done( function() {
		var eReportTypeSelected = content.find( 'input[type=radio]:checked' ).val();
		if ( eReportTypeSelected )
		{
			$J.post(
				'http://store.steampowered.com/appreport/' + nAppId + '/report/',
				{
					'report_type' : eReportTypeSelected,
					'report_reason' : content.find( 'textarea' ).val(),
					'sessionid': g_sessionID
				}
			).done( function( json ) {
					gReportedApp = true;
					$J( "#ReportAppBtn").addClass( 'btn_active' );
				}
			).fail( function( jqXHR ) {
					var json = jqXHR.responseText.evalJSON();
					if ( json.success == 29 )
					{
						ShowAlertDialog( 'Σφάλμα', 'Έχετε ήδη αναφέρει αυτό το προϊόν!' );
						gReportedApp = true;
						$J( "#ReportAppBtn").addClass( 'btn_active' );
					}
					else
					{
						ShowAlertDialog( 'Σφάλμα', 'Υπήρξε ένα πρόβλημα με την αποθήκευση της αναφοράς σας. Παρακαλούμε δοκιμάστε ξανά αργότερα.' );
					}

				} );
		}
		else
		{
			ShowAlertDialog( 'Σφάλμα', 'Πρέπει να επιλέξετε ένα λόγο για τον οποίο αναφέρετε αυτό το προϊόν!' );
		}
	});
}

function ShowGotSteamModal( strSteamURL, strAppName, strPlayLaunchVerb )
{
		var $ModalContent = $J("<div class=\"gotsteamModal\">\r\n\t<div class=\"got_steam_ctn\">\r\n\t<div class=\"got_steam_box\">\r\n\t\t<h1>\u0388\u03c7\u03b5\u03c4\u03b5 \u03c4\u03bf Steam;<\/h1>\r\n\t\t<p>\u03a0\u03c1\u03ad\u03c0\u03b5\u03b9 \u03bd\u03b1 \u03ad\u03c7\u03b5\u03c4\u03b5 \u03c4\u03b7\u03bd <a href=\"http:\/\/store.steampowered.com\/about\/\">\u03b5\u03c6\u03b1\u03c1\u03bc\u03bf\u03b3\u03ae Steam \u03b3\u03b9\u03b1 \u03c5\u03c0\u03bf\u03bb\u03bf\u03b3\u03b9\u03c3\u03c4\u03ae<\/a> \u03b5\u03b3\u03ba\u03b1\u03c4\u03b5\u03c3\u03c4\u03b7\u03bc\u03ad\u03bd\u03b7 \u03c0\u03c1\u03b9\u03bd \u03bc\u03c0\u03bf\u03c1\u03ad\u03c3\u03b5\u03c4\u03b5 \u03bd\u03b1 \u03b5\u03b3\u03ba\u03b1\u03c4\u03b1\u03c3\u03c4\u03ae\u03c3\u03b5\u03c4\u03b5 \u03ba\u03b1\u03b9 \u03bd\u03b1 \u03b5\u03ba\u03ba\u03b9\u03bd\u03ae\u03c3\u03b5\u03c4\u03b5 \u03c4\u03bf <strong class=\"gotSteam_AppName\"><\/strong>. \u0388\u03c7\u03b5\u03c4\u03b5 \u03c4\u03bf Steam \u03b5\u03b3\u03ba\u03b1\u03c4\u03b5\u03c3\u03c4\u03b7\u03bc\u03ad\u03bd\u03bf \u03c3\u03b5 \u03b1\u03c5\u03c4\u03cc\u03bd \u03c4\u03bf\u03bd \u03c5\u03c0\u03bf\u03bb\u03bf\u03b3\u03b9\u03c3\u03c4\u03ae;<\/p>\r\n\t\t<div class=\"gotsteam_buttons\">\r\n\t\t\t<a class=\"gotSteam_SteamURL btn_blue leftbtn\" href=\"\">\r\n\t\t\t\t<h3>\u039d\u03b1\u03b9, \u03c4\u03bf Steam \u03b5\u03af\u03bd\u03b1\u03b9 \u03b5\u03b3\u03ba\u03b1\u03c4\u03b5\u03c3\u03c4\u03b7\u03bc\u03ad\u03bd\u03bf<\/h3>\r\n\t\t\t\t<h5 class=\"gotsteam_action\"><\/h5>\r\n\t\t\t<\/a>\r\n\t\t\t<a href=\"http:\/\/store.steampowered.com\/about\/\" class=\"btn_blue\">\r\n\t\t\t\t<h3>\u038c\u03c7\u03b9, \u03c7\u03c1\u03b5\u03b9\u03ac\u03b6\u03bf\u03bc\u03b1\u03b9 \u03c4\u03bf Steam<\/h3>\r\n\t\t\t\t<h5>\u0394\u03b9\u03b1\u03b2\u03ac\u03c3\u03c4\u03b5 \u03c3\u03c7\u03b5\u03c4\u03b9\u03ba\u03ac \u03ba\u03b1\u03b9 \u03ba\u03b1\u03c4\u03b5\u03b2\u03ac\u03c3\u03c4\u03b5 \u03c4\u03bf Steam<\/h5>\r\n\t\t\t<\/a>\r\n\t\t\t<div style=\"clear: left;\"><\/div>\r\n\t\t<\/div>\r\n\t\t<div class=\"got_steam_low_block\">\r\n\t\t\t<div class=\"gotsteam_steam_ico\"><img src=\"http:\/\/store.akamai.steamstatic.com\/public\/images\/v6\/steam_ico.png\" width=\"40\" height=\"40\" border=\"0\" \/><\/div>\r\n\t\t\t\u03a4\u03bf Steam \u03b5\u03af\u03bd\u03b1\u03b9 \u03b7 \u03bd\u03bf\u03cd\u03bc\u03b5\u03c1\u03bf \u03ad\u03bd\u03b1 \u03c0\u03bb\u03b1\u03c4\u03c6\u03cc\u03c1\u03bc\u03b1 desktop gaming. \u0395\u03af\u03bd\u03b1\u03b9 \u03b4\u03c9\u03c1\u03b5\u03ac\u03bd \u03ba\u03b1\u03b9 \u03b5\u03cd\u03ba\u03bf\u03bb\u03bf \u03c3\u03c4\u03b7\u03bd \u03c7\u03c1\u03ae\u03c3\u03b7. <a href=\"http:\/\/store.steampowered.com\/about\/\">\u039c\u03ac\u03b8\u03b5\u03c4\u03b5 \u03c0\u03b5\u03c1\u03b9\u03c3\u03c3\u03cc\u03c4\u03b5\u03c1\u03b1 \u03b3\u03b9\u03b1 \u03c4\u03bf Steam.<\/a>\r\n\t\t<\/div><\/div>\r\n\t<\/div>\r\n<\/div>");
	$ModalContent.find('.gotSteam_AppName').text( strAppName );
	$ModalContent.find('.gotsteam_action').text( strPlayLaunchVerb );
	$ModalContent.find( '.gotSteam_SteamURL').attr( 'href', strSteamURL );
	var Modal = new CModal( $ModalContent );
	Modal.Show();
	//ShowDialog( 'Got Steam?', $ModalContent );
}

function ChangeSeason( el, season )
{
	// switch active season indicator
	$J( '.series_seasons .season_selector .season_name' ).removeClass( 'active' );
	$J( el ).addClass( 'active' );
	
	// switch which episodes are shown
	$J( '.season_episode_list_wrapper .season_episode_list' ).removeClass( 'active' );
	$J( '.season_episode_list_wrapper .season_episode_list[data-season="' + season + '"]' ).addClass( 'active' );

	// load the images
	$J( '.season_episode_list.active .episode_image_col img').each( function( index, value )
	{
		$J( this ).attr( 'src', $J( this ).data( 'src' ) );
	});
}

function ShowRecommendedMoreInfoModal()
{
	var strTemplate = "<div class=\"recommended_more_info_modal\">\r\n\t\t\t\t\t\t\t\t<p class=\"intro\">\u0392\u03c1\u03ae\u03ba\u03b1\u03c4\u03b5 \u03ba\u03ac\u03c4\u03b9 \u03c0\u03bf\u03c5 \u03b4\u03b5\u03bd \u03bc\u03bf\u03b9\u03ac\u03b6\u03b5\u03b9 \u03bc\u03b5 \u03ac\u03bb\u03bb\u03b1 \u03c0\u03bf\u03c5 \u03ad\u03c7\u03b5\u03c4\u03b5 \u03c7\u03c1\u03b7\u03c3\u03b9\u03bc\u03bf\u03c0\u03bf\u03b9\u03ae\u03c3\u03b5\u03b9 \u03c3\u03c4\u03bf \u03c0\u03b1\u03c1\u03b5\u03bb\u03b8\u03cc\u03bd. \u03a4\u03bf Steam \u03b8\u03b1 \u03bc\u03b1\u03b8\u03b1\u03af\u03bd\u03b5\u03b9 \u03c4\u03b9\u03c2 \u03c0\u03c1\u03bf\u03c4\u03b9\u03bc\u03ae\u03c3\u03b5\u03b9\u03c2 \u03c3\u03b1\u03c2 \u03b1\u03c0\u03cc \u03c0\u03b1\u03b9\u03c7\u03bd\u03af\u03b4\u03b9\u03b1 \u03c0\u03bf\u03c5 \u03c0\u03b1\u03af\u03b6\u03b5\u03c4\u03b5, \u03c4\u03b1\u03b9\u03bd\u03af\u03b5\u03c2 \u03c0\u03bf\u03c5 \u03c0\u03b1\u03c1\u03b1\u03ba\u03bf\u03bb\u03bf\u03c5\u03b8\u03b5\u03af\u03c4\u03b5 \u03ba\u03b1\u03b9 \u03bb\u03bf\u03b3\u03b9\u03c3\u03bc\u03b9\u03ba\u03cc \u03c0\u03bf\u03c5 \u03c7\u03c1\u03b7\u03c3\u03b9\u03bc\u03bf\u03c0\u03bf\u03b9\u03b5\u03af\u03c4\u03b5 \u03c3\u03c4\u03bf Steam. \u0398\u03b1 \u03c7\u03c1\u03b7\u03c3\u03b9\u03bc\u03bf\u03c0\u03bf\u03b9\u03ae\u03c3\u03bf\u03c5\u03bc\u03b5 \u03b1\u03c5\u03c4\u03ae \u03c4\u03b7 \u03b4\u03c1\u03b1\u03c3\u03c4\u03b7\u03c1\u03b9\u03cc\u03c4\u03b7\u03c4\u03b1 \u03b3\u03b9\u03b1 \u03bd\u03b1 \u03c0\u03c1\u03bf\u03c3\u03b1\u03c1\u03bc\u03cc\u03c3\u03bf\u03c5\u03bc\u03b5 \u03c4\u03b9\u03c2 \u03c0\u03c1\u03bf\u03c4\u03ac\u03c3\u03b5\u03b9\u03c2 \u03bc\u03b1\u03c2 \u03c3\u03c4\u03b9\u03c2 \u03c0\u03c1\u03bf\u03c4\u03b9\u03bc\u03ae\u03c3\u03b5\u03b9\u03c2 \u03c3\u03b1\u03c2.<\/p>\r\n\t\t\t\t\t\t\t\t<h2>\u03a3\u03b1\u03c2 \u03b1\u03c1\u03ad\u03c3\u03b5\u03b9 \u03b1\u03c5\u03c4\u03cc \u03c4\u03bf \u03b1\u03bd\u03c4\u03b9\u03ba\u03b5\u03af\u03bc\u03b5\u03bd\u03bf;<\/h2>\r\n\t\t\t\t\t\t\t\t<p>\u0395\u03ac\u03bd \u03c3\u03b1\u03c2 \u03b1\u03c1\u03ad\u03c3\u03b5\u03b9 \u03b1\u03c5\u03c4\u03cc \u03c4\u03bf \u03b1\u03bd\u03c4\u03b9\u03ba\u03b5\u03af\u03bc\u03b5\u03bd\u03bf \u03ba\u03b1\u03b9 \u03c4\u03bf \u03b1\u03b3\u03bf\u03c1\u03ac\u03c3\u03b5\u03c4\u03b5 \u03ba\u03b1\u03b9 \u03c0\u03b1\u03af\u03be\u03b5\u03c4\u03b5, \u03b8\u03b1 \u03c4\u03bf \u03bb\u03ac\u03b2\u03bf\u03c5\u03bc\u03b5 \u03c5\u03c0\u03cc\u03c8\u03b7 \u03ba\u03b1\u03c4\u03ac \u03c4\u03b9\u03c2 \u03bc\u03b5\u03bb\u03bb\u03bf\u03bd\u03c4\u03b9\u03ba\u03ad\u03c2 \u03c0\u03c1\u03bf\u03c4\u03ac\u03c3\u03b5\u03b9\u03c2 \u03bc\u03b1\u03c2.<\/p>\r\n\t\t\t\t\t\t\t\t<h2>\u0394\u03b5\u03bd \u03c3\u03b1\u03c2 \u03b5\u03bd\u03b4\u03b9\u03b1\u03c6\u03ad\u03c1\u03bf\u03c5\u03bd \u03c0\u03c1\u03ac\u03b3\u03bc\u03b1\u03c4\u03b1 \u03c3\u03b1\u03bd \u03b1\u03c5\u03c4\u03cc;<\/h2>\r\n\t\t\t\t\t\t\t\t<p>\u039c\u03c0\u03bf\u03c1\u03b5\u03af\u03c4\u03b5 \u03b5\u03c0\u03af\u03c3\u03b7\u03c2 \u03bd\u03b1 \u03b4\u03b9\u03b1\u03bc\u03bf\u03c1\u03c6\u03ce\u03c3\u03b5\u03c4\u03b5 \u03c4\u03b9\u03c2 \u03c0\u03c1\u03bf\u03c4\u03b9\u03bc\u03ae\u03c3\u03b5\u03b9\u03c2 \u03ba\u03b1\u03c4\u03b1\u03c3\u03c4\u03ae\u03bc\u03b1\u03c4\u03cc\u03c2 \u03c3\u03b1\u03c2 \u03b3\u03b9\u03b1 \u03bd\u03b1 \u03c5\u03c0\u03bf\u03b4\u03b5\u03af\u03be\u03b5\u03c4\u03b5 \u03c3\u03c4\u03bf Steam \u03b5\u03c4\u03b9\u03ba\u03ad\u03c4\u03b5\u03c2 \u03ae \u03c4\u03cd\u03c0\u03bf\u03c5\u03c2 \u03c0\u03c1\u03bf\u03ca\u03cc\u03bd\u03c4\u03c9\u03bd \u03c0\u03bf\u03c5 \u03b4\u03b5\u03bd \u03c3\u03b1\u03c2 \u03b5\u03bd\u03b4\u03b9\u03b1\u03c6\u03ad\u03c1\u03bf\u03c5\u03bd. <a href=\"https:\/\/store.steampowered.com\/account\/preferences\/\">\u0395\u03c0\u03b9\u03c3\u03ba\u03b5\u03c6\u03b8\u03b5\u03af\u03c4\u03b5 \u03c4\u03b9\u03c2 \u03c0\u03c1\u03bf\u03c4\u03b9\u03bc\u03ae\u03c3\u03b5\u03b9\u03c2 \u03ba\u03b1\u03c4\u03b1\u03c3\u03c4\u03ae\u03bc\u03b1\u03c4\u03bf\u03c2<\/a>.<\/p>\r\n\t\t\t\t\t\t\t<\/div>";
	ShowAlertDialog( "\u03a0\u03b5\u03c1\u03b9\u03c3\u03c3\u03cc\u03c4\u03b5\u03c1\u03b1 \u03b3\u03b9\u03b1 \u03c4\u03b9\u03c2 \u03c0\u03c1\u03bf\u03c4\u03ac\u03c3\u03b5\u03b9\u03c2", strTemplate);

}

function CollapseLongStrings( strSelector )
{

	$J(strSelector).each(function(i, j){
		var $target = $J(j);
		if( j.scrollWidth >  $target.innerWidth() )
		{
			//$target.css({ 'overflow': 'hidden', 'white-space': 'nowrap', 'text-overflow': 'ellipsis' });
			var elMoreBtn = document.createElement('div');

			elMoreBtn.classList.add('more_btn');
			elMoreBtn.textContent = '+';
			elMoreBtn.addEventListener('click', function($target, elButton, event){

				$target.css({'overflow': 'visible', 'white-space': 'normal'});
				elButton.remove();

			}.bind(null, $target, elMoreBtn ));

			j.parentNode.appendChild(elMoreBtn);
		}
	})
}

function JSReportProductAction( appId, pageAction, snr )
{
	$J.post( 'http://store.steampowered.com//ajaxreportproductaction/' + appId + '/', {
			'page_action' : pageAction,
			'snr' : snr
		}
	);
}

