
var gameapi = angular.module('gameapi', ['ngRoute' , 'tc.chartjs', 'chart.js']);

gameapi.config(function($routeProvider) {
	$routeProvider
	.when('/mylibrary', {
		templateUrl : 'mylibrary.html'
	})
	.when('/search', {
		templateUrl : 'search.html'
	})
	.when('/summary', {
		templateUrl : 'summary.html'
	})
	.when('/wishlist', {
		templateUrl : 'wishlist.html'
	})
	.when('/stack', {
		templateUrl : 'stack.html'
	})
	.otherwise({
		redirectTo: '/mylibrary'
	});
});



gameapi.controller('gamecontroller', function($scope, $http) {
	

	/* -- Game View Variables ---*/
	
	$scope.appName = 'Throne of Games';
	$scope.gameToUpdate = {};
	$scope.update = {
			console: "Playstation 4",
			condition: "CIB"
	};
	$scope.isSearchRunning = false;
	$scope.deleteId = 0;
	$scope.search = {
			text: "", 
			console: "Playstation 4", 
			condition: "CIB"
	};
	
	$scope.coverSizes = {
			t_cover_small: "t_cover_small",
			screenshot_med: "screenshot_med",
			t_cover_big: "t_cover_big",
			t_logo_med: "t_logo_med",
			t_screenshot_big: "t_screenshot_big",
			t_screenshot_huge: "t_screenshot_huge",
			t_thumb: "t_thumb",
			t_micro: "t_micro",
			t_720p: "t_720p",
			t_1080p: "t_1080p"
	};	
	$scope.library= {
			totalRetailValue: 0,
			totalTradeInValue: 0
	};
	
	$scope.wishlist= {
			totalRetailPrice: 0
	};
	
	
	/*---------Search View Variables---------*/	
	
	$scope.pieChartData = function() {
		var data = [0,0,0,0,0,0,0];
		for (i = 0; i < $scope.games.length; i++) {
			var game = $scope.games[i];
			if(game.console == 'Playstation 3') {
				data[0] += 1;
			}
			else if(game.console == 'Playstation 4') {
				data[1] += 1;
			}
			else if(game.console == 'Xbox One') {
				data[2] += 1;
			}
			else if(game.console == 'Xbox 360') {
				data[3] += 1;
			}
			else if(game.console == 'Wii') {
				data[4] += 1;
			}
			else if(game.console == 'Wii U') {
				data[5] += 1;
			}
			else if(game.console == 'Nintendo Switch') {
				data[6] += 1;
			}
		}		
		$scope.options= {
			title: {
				display: true,
				text: 'Number of Games by Console',
				fontSize: '18',
				fontColor: 'whitesmoke',
				position: 'bottom',
			},
			legend: {
				display: true,
				labels: {
	    			fontColor: 'lightgrey'
	    		}
			}
		};
		
	    $scope.data= {
	        labels: ["PS3", "PS4", "XBOX ONE", "XBOX 360", "WII", "WII U", "NINTENDO SWITCH"],
	        datasets: [{
	            label: '# of Games per Console',
	            data: data,
	            backgroundColor: [
	                'rgba(211, 47, 47, 0.3)',
	                'rgba(33, 150, 243, 0.3)',
	                'rgba(255, 205, 210, 0.3)',
	                'rgba(255, 87, 34, 0.3)',
	                'rgba(76, 175, 80, 0.3)',
	                'rgba(156, 39, 176, 0.3)',
	                'rgba(255, 235, 59, 0.3)'
	            ],
	            borderColor: [
	                'rgba(211, 47, 47, 1)',
	                'rgba(33, 150, 243, 1)',
	                'rgba(255, 205, 210, 1)',
	                'rgba(255, 87, 34, 1)',
	                'rgba(76, 175, 80, 1)',
	                'rgba(156, 39, 176, 1)',
	                'rgba(255, 235, 59, 1)'
	            ],
	            borderWidth: 2
	        }]
	    }
    
	};
	
	$scope.calculateLibraryTotals = function() {
		$scope.library.totalRetailValue = 0;
		$scope.library.totalTradeInValue = 0;
		$scope.wishlist.totalRetailPrice = 0;
		
		var i;
		for (i = 0; i < $scope.games.length; i++) {
			var game = $scope.games[i];
			if(game.location == 'LIBRARY') {
				if(game.condition == 'NEW') {
					$scope.library.totalRetailValue += game.gamePricing.retailNewBuy;
				}
				else if(game.condition == 'CIB') {
					$scope.library.totalRetailValue += game.gamePricing.retailCibBuy;
				}
				else if(game.condition == 'LOOSE') {
					$scope.library.totalRetailValue += game.gamePricing.retailLooseBuy;
				}
				
				$scope.library.totalTradeInValue += game.gamePricing.gameStopTradePrice;
			}
			if(game.location == 'WISHLIST') {
				if(game.condition == 'NEW') {
					$scope.wishlist.totalRetailPrice += game.gamePricing.retailNewSell;
				}
				else if(game.condition == 'CIB') {
					$scope.wishlist.totalRetailPrice += game.gamePricing.retailCibSell;
				}
				else if(game.condition == 'LOOSE') {
					$scope.wishlist.totalRetailPrice += game.gamePricing.retailLooseSell;
				}
			}
		}
	};
	
	$scope.getSizedCoverUrl = function(url, size) {
		return url.replace('t_thumb', size);
	};
	
	$scope.setDeleteId = function(id){
		$scope.deleteId = id;
	};
	
	
	$scope.searchIgdb = function() {
		$scope.isSearchRunning = true;
		$scope.igdbSearchResults = []; 		 
		
		$http.get("https://api-endpoint.igdb.com/games/?search="+$scope.search.text+"&fields=*", {
		    headers: {
		      "user-key": "API KEY FROM IGDB GOES HERE",
		      Accept: "application/json"
		    }
		  })
		  .then(function(response) {
		    // Do work here
			  $scope.igdbSearchResults = response.data;
			  console.log($scope.igdbSearchResults);
			  $scope.isSearchRunning = false;
		  })
		  .catch(function(response) {
		    console.log("error", e);
		  });
		
	};
	
	$scope.getGames = function() {		
		$scope.games = [];
		$http.get("/gameapi/service/v1/games")
		.then(function(response) {
			$scope.games = response.data;
			console.log($scope.games[0]);
			$scope.calculateLibraryTotals();
		}, function(response) {
			console.log('error HTTP GET games: ' + response.status);
		});
	};
	
	
	$scope.deleteGame = function() {	
		console.log('delete game: ' + $scope.deleteId);
		$http.delete("/gameapi/service/v1/games/" + $scope.deleteId)
		.then(function(response) {				
			setTimeout( ()=>{ $scope.getGames(); }, 500 );			
		}, function(response) {
			console.log('error HTTP DELETE games: ' + response.status);
		});
	};
	
	$scope.postGame = function(igdbGameJson, location) {
		
		var game = {};
		game.id = 0;
		game.name = igdbGameJson.name;
		game.releaseDate = igdbGameJson.release_dates ? igdbGameJson.release_dates[0].date : 0;
		game.summary = igdbGameJson.summary;
		game.coverUrl = igdbGameJson.cover.url;
		game.rating = igdbGameJson.rating ? igdbGameJson.rating : 0;
		game.console = $scope.search.console;
		game.condition = $scope.search.condition;
		game.location = location;
		
		var jsonObject = angular.toJson(game, true);
		
		console.log('Add game: ' + jsonObject);
		$http.post("/gameapi/service/v1/games", jsonObject)
		.then(
			function success(response) {
				console.log('success: ' + response.data);
			},
			function error(response) {
				console.log('error, return status: ' + response.status);
				$scope.updateStatus = 'add error, ' + response.data.message;
				console.log(jsonObject);
			}
		);
	};
	
	$scope.setGameToUpdate = function (game) {
		$scope.gameToUpdate = game;
		$scope.update.condition = $scope.gameToUpdate.condition;
		$scope.update.console = $scope.gameToUpdate.console;
	};
	
	$scope.updateGame = function () {
		$scope.gameToUpdate.condition = $scope.update.condition;
		$scope.gameToUpdate.console = $scope.update.console;
		var jsonObject = angular.toJson($scope.gameToUpdate, true);
		$http.put("/gameapi/service/v1/games", jsonObject)
		.then(
				function success(response) {
					
				},
				function error(response) {
					console.log('error, return status: ' + response.status);
					$scope.updateStatus = 'add error, ' + response.data.message;
				}
		);
	};
	
	$scope.updateGameLocation = function (game, newLocation) {
		game.location = newLocation;
		
		var jsonObject = angular.toJson(game, true);
		$http.put("/gameapi/service/v1/games", jsonObject)
		.then(
				function success(response) {
					$scope.calculateLibraryTotals();
				},
				function error(response) {
					console.log('error, return status: ' + response.status);
					$scope.updateStatus = 'add error, ' + response.data.message;
				}
		);
	};
	
	$scope.initProgress = function (id, rating) {
		if(document.getElementById(id)) {
			document.getElementById(id).innerHTML="";
		}		
		setTimeout(function() {
			if(document.getElementById(id)) {
				var bar = new ProgressBar.Circle("#" +id, {
					  color: '#303030',
					  // This has to be the same size as the maximum width to
					  // prevent clipping
					  strokeWidth: 6,
					  trailWidth: 1,
					  easing: 'easeInOut',
					  duration: 1400,
					  text: {
					    autoStyleContainer: false
					  },
					  from: { color: '#FF000', width: 6 },
					  to: { color: '#00FF00', width: 6 },
					  // Set default step function for all animate calls
					  step: function(state, circle) {
					    circle.path.setAttribute('stroke', state.color);
					    circle.path.setAttribute('stroke-width', state.width);
	
					    var value = Math.round(circle.value() * 100);
					    if (value === 0) {
					      circle.setText('');
					    } else {
					      circle.setText(value);
					    }
	
					  }
					});
				
					bar.text.style.fontFamily = '"Raleway", Helvetica, sans-serif';
					bar.text.style.fontSize = '3rem';
	
					bar.animate(rating/100);  // Number from 0.0 to 1.0
				}
		}, 500);
	};
	
	
	
	/* when the page first loads, then get all games */
	$scope.getGames();
});