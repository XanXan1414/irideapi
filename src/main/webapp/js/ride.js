/**
 * 
 */




var irideapp = angular.module('irideapp',['ngRoute']);

irideapp.config(function($routeProvider){
		$routeProvider
		.when('/ride',{
			templateUrl: 'ride.html'
		})
		.when('/map',{
			templateUrl: 'mapl.html'
		})
		.when('/events',{
			templateUrl: 'events.html'
		})
		.when('/resume',{
			templateUrl: 'resume1.html'
		})
		.when('/search',{
			templateUrl: 'search.html'
		})
		.when('/email',{
			templateUrl: 'email.html',
			controller : 'rideEmailController'
		})
		.when('/create',{
			templateUrl: 'create.html',
			controller : 'rideCreateController'
		})
		.when('/updateDelete',{
			templateUrl: 'updateDelete.html'
		})
		.when('/signup',{
			templateUrl: 'signup.html',
			controller : 'rideEmailController'
		})
		.when('/stack',{
			templateUrl: 'stack.html'
		})
		.otherwise({
			templateUrl: 'mapl.html'
		});
		
		
});

irideapp.controller('ridecontroller', function($scope, $http){
	$scope.appName = 'IrideAR';
	
	$scope.showSearch = true;
	$scope.showEditDelete = false;
	
	
     $scope.loadMap = function() {
    	 var styledMapType = new google.maps.StyledMapType(
    	            [
    	              {elementType: 'geometry', stylers: [{color: '#ebe3cd'}]},
    	              {elementType: 'labels.text.fill', stylers: [{color: '#523735'}]},
    	              {elementType: 'labels.text.stroke', stylers: [{color: '#f5f1e6'}]},
    	              {
    	                featureType: 'administrative',
    	                elementType: 'geometry.stroke',
    	                stylers: [{color: '#c9b2a6'}]
    	              },
    	              {
    	                featureType: 'administrative.land_parcel',
    	                elementType: 'geometry.stroke',
    	                stylers: [{color: '#dcd2be'}]
    	              },
    	              {
    	                featureType: 'administrative.land_parcel',
    	                elementType: 'labels.text.fill',
    	                stylers: [{color: '#ae9e90'}]
    	              },
    	              {
    	                featureType: 'landscape.natural',
    	                elementType: 'geometry',
    	                stylers: [{color: '#dfd2ae'}]
    	              },
    	              {
    	                featureType: 'poi',
    	                elementType: 'geometry',
    	                stylers: [{color: '#dfd2ae'}]
    	              },
    	              {
    	                featureType: 'poi',
    	                elementType: 'labels.text.fill',
    	                stylers: [{color: '#93817c'}]
    	              },
    	              {
    	                featureType: 'poi.park',
    	                elementType: 'geometry.fill',
    	                stylers: [{color: '#a5b076'}]
    	              },
    	              {
    	                featureType: 'poi.park',
    	                elementType: 'labels.text.fill',
    	                stylers: [{color: '#447530'}]
    	              },
    	              {
    	                featureType: 'road',
    	                elementType: 'geometry',
    	                stylers: [{color: '#f5f1e6'}]
    	              },
    	              {
    	                featureType: 'road.arterial',
    	                elementType: 'geometry',
    	                stylers: [{color: '#fdfcf8'}]
    	              },
    	              {
    	                featureType: 'road.highway',
    	                elementType: 'geometry',
    	                stylers: [{color: '#f8c967'}]
    	              },
    	              {
    	                featureType: 'road.highway',
    	                elementType: 'geometry.stroke',
    	                stylers: [{color: '#e9bc62'}]
    	              },
    	              {
    	                featureType: 'road.highway.controlled_access',
    	                elementType: 'geometry',
    	                stylers: [{color: '#e98d58'}]
    	              },
    	              {
    	                featureType: 'road.highway.controlled_access',
    	                elementType: 'geometry.stroke',
    	                stylers: [{color: '#db8555'}]
    	              },
    	              {
    	                featureType: 'road.local',
    	                elementType: 'labels.text.fill',
    	                stylers: [{color: '#806b63'}]
    	              },
    	              {
    	                featureType: 'transit.line',
    	                elementType: 'geometry',
    	                stylers: [{color: '#dfd2ae'}]
    	              },
    	              {
    	                featureType: 'transit.line',
    	                elementType: 'labels.text.fill',
    	                stylers: [{color: '#8f7d77'}]
    	              },
    	              {
    	                featureType: 'transit.line',
    	                elementType: 'labels.text.stroke',
    	                stylers: [{color: '#ebe3cd'}]
    	              },
    	              {
    	                featureType: 'transit.station',
    	                elementType: 'geometry',
    	                stylers: [{color: '#dfd2ae'}]
    	              },
    	              {
    	                featureType: 'water',
    	                elementType: 'geometry.fill',
    	                stylers: [{color: '#b9d3c2'}]
    	              },
    	              {
    	                featureType: 'water',
    	                elementType: 'labels.text.fill',
    	                stylers: [{color: '#92998d'}]
    	              }
    	            ],
    	            {name: 'Styled Map'});

    	        // Create a map object, and include the MapTypeId to add
    	        // to the map type control.
    	        var map = new google.maps.Map(document.getElementById('map'), {
    	          center: {lat: 35.0887, lng: -92.4421},
    	          zoom: 11,
    	          mapTypeControlOptions: {
    	            mapTypeIds: ['roadmap', 'satellite', 'hybrid', 'terrain',
    	                    'styled_map']
    	          }
    	        });

    	        //Associate the styled map with the MapTypeId and set it to display.
    	        map.mapTypes.set('styled_map', styledMapType);
    	        map.setMapTypeId('styled_map');
	
	    var trafficLayer = new google.maps.TrafficLayer();
	    trafficLayer.setMap(map);
     }

     $scope.getRides = function() {
 		console.log('get all the rides');
 		$scope.rides = [{"title":"retrieving ride maps.."}];
 		
 		$http.get("/irideapi/iride/rides")
 		.then(function(response) {
 			$scope.rides= response.data;
 			console.log('number of rides: ' + $scope.rides.length);
 		}, function(response) {
 			console.log('error HTTP GET rides: ' + response.status);
 		});
 	}
     
     $scope.updateRide = function(rideToUpdate) {
 		console.log('selected ride to update: ' + angular.toJson(rideToUpdate));
 		$scope.rideToUpdate = angular.copy(rideToUpdate);
 		$scope.showEditDelete = true;
 		$scope.showSearch = false;
 		$scope.isUpdateButtonDisabled = false;
 		$scope.isDeleteButtonDisabled = false;
 		$scope.updateStatus = '';
 	};
 	
 	$scope.returnToSearch = function() {
		$scope.showEditDelete = false;
		$scope.showSearch =true;
		$scope.getRides();
	}
 	
 	$scope.deleteRide = function(id) {		
		console.log('delete ride: ' + id);
		$http.delete("/irideapi/iride/rides/" + id)
		.then(function(response) {	
			$scope.isUpdateButtonDisabled = true;
			$scope.isDeleteButtonDisabled = true;
			$scope.updateStatus = 'delete successful';
			console.log('number of rides deleted: ' + response.data.length);			
		}, function(response) {
			console.log('error HTTP DELETE rides: ' + response.status);
			$scope.updateStatus = 'delete error, ' + response.data.message;
		});
	}
 	
 	$scope.putRide = function(rideToUpdate) {
		$scope.jsonObject = angular.toJson(rideToUpdate, false);
		console.log('update ride:' + $scope.jsonObject);
		
		$http.put("/irideapi/iride/rides", $scope.jsonObject)
		.then(
				function success(response) {
					$scope.isUpdateButtonDisabled = true;
					console.log('status: ' + response.status);
					$scope.updateStatus = 'update successful';
				},
				function error(response) {
					console.log('error, return status: ' + response.status);
					$scope.updateStatus = 'update error, ' + response.data.message;
				}
				);
	};
	
	$scope.getRides();
  
});

irideapp.controller('rideCreateController', function($scope, $http ) {	
	
	$scope.postRide = function() {	
		$scope.jsonObject = angular.toJson($scope.newRide, false);
		console.log('new ride: ' + $scope.jsonObject);		
		
		$http.post("/irideapi/iride/rides", $scope.jsonObject)
		.then(
				function success(response) {					
					console.log('status: ' + response.status);
					$scope.createStatus = 'successful insert of new ride';
					$scope.successfulInsert = true;
				},
				function error(response) {
					console.log('error, return status: ' + response.status);
					$scope.createStatus = 'insert error, ' + response.data.message;
				}
		);	
		$scope.returnToSearch();
	};
	
	$scope.clearRide = function() {
		$scope.createStatus = 'Enter new ride information';
		$scope.successfulInsert = false;		
		$scope.newRide = {
				title : '',
				description : '',
				genre : '',
				releaseYear : ''
				
		};
	}
	
});

irideapp.controller('rideEmailController', function($scope, $http) {
	

	$scope.emailCustomers = function() {
		var email = {
				emailSubject : $scope.emailSubject,
				emailText : $scope.emailText				
		};
		
		$scope.jsonEmailObject = angular.toJson(email, false);
		console.log('email customers: ' + $scope.jsonEmailObject);				
			
		$http.post("/irideapi/iride/rides/email", $scope.jsonEmailObject)
		.then(
				function success(response) {
					$scope.emailStatus = "success. press 'Clear' to enter new email";								
				},
				function error(response) {
					console.log('error sending email, status: ' + response.status);
					$scope.emailStatus = "error. press 'Clear' to try again";						
				}				
		);			
	};	
	
	$scope.emailCustomersClear = function() {
		console.log('email customers clear');
		$scope.emailSubject = '';
		$scope.emailText = '';
		$scope.emailStatus = '';
	};
	
	$scope.subscribe = function() {
		var emailAddress = {
				address : $scope.address
		};
		$scope.jsonEmailAddressObject = angular.toJson(emailAddress,false);
		console.log('email address: ' + $scope.jsonEmailAddressObject)
		
		$http.post("/irideapi/iride/rides/subscribe", $scope.jsonEmailAddressObject)
		.then(
				function success(response) {
					$scope.emailAddressStatus = "success. A confirmation email is on its way!";								
				},
				function error(response) {
					console.log('error sending email, status: ' + response.status);
					$scope.emailAddressStatus = "error. please try again";						
				}	
				);
		};
});

