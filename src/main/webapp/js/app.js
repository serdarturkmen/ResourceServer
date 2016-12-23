'use strict';

var app = angular.module('resourceApp',[]);

app.config(function($locationProvider){
    $locationProvider.html5Mode(true);
});

app.controller('NavCtrl', function($scope, $window, $location, $http){

	$scope.logoutUrl = 'http://localhost:10001'
    $scope.authUrl = 'http://localhost:10000/oauth/authorize?client_id=8ddsa565dsa87787IuJ6NVIBDKCh4cBdslds65ds&response_type=token&scope=write&redirect_uri=http://localhost:10001/index/';
    
    $scope.token;
    
    $scope.login = function(){
        $window.location.href = $scope.authUrl;
        //$window.location.reload();
    };
   
   $scope.logout = function(){
       var token = $window.sessionStorage.getItem('token');
       console.log('Token : '+token);
       $window.sessionStorage.removeItem('token');
   };
   
   $scope.getTokenFromUrl = function(){
       var token;
       var hashParams = $location.hash();
       if(!hashParams) {
           console.log("hash yok");
           return;
       }
       console.log(hashParams);
       var eachParam = hashParams.split('&');
       for(var i=0; i<eachParam.length; i++){
           var param = eachParam[i].split('=');
           if('access_token' === param[0]) {
               token = param[1];
           }
       }
       console.log("Access Token : "+token);
       if(token){
           $window.sessionStorage.setItem('token', token);
           $scope.saveToken(token);
       }
       $location.hash('');
   };
   
   $scope.checkLogin = function(){
       if($window.sessionStorage.getItem('token')){
           $scope.token = $window.sessionStorage.getItem('token');
           return;
       }
       $scope.getTokenFromUrl();
       $scope.login();
   };
   
   $scope.saveToken = function(token) {
	   $http.get('http://localhost:10001/api/success?access_token='+token)
       .success(function(data){
          $scope.staffOutput = data;
          $scope.currentUser = data.user;
       }).error(function(data, status){
           alert("Error bos : "+status);
           console.log(data);
           $scope.staffOutput = data;
       });
   }
   
   $scope.checkLogin();
});

app.controller('OauthCtrl', function($scope, $http, $window){
   $scope.currentUser; 
   $scope.accessToken = $window.sessionStorage.getItem('token');
    
   $scope.adminApi = function(){
       if(!$scope.accessToken) {
           alert("token yok");
           return;
       }
       $http.get('http://localhost:10001/api/admin?access_token='+$scope.accessToken)
           .success(function(data){
               $scope.adminOutput = data;
               $scope.currentUser = data.user;
            }).error(function(data, status){
                alert("Error bos : "+status);
                console.log(data);
                $scope.adminOutput = data;
            });
   }; 
   
   $scope.userApi = function(){
       if(!$scope.accessToken) {
           alert("token yok");
           return;
       }
       $http.get('http://localhost:10001/api/staff?access_token='+$scope.accessToken)
            .success(function(data){
               $scope.staffOutput = data;
               $scope.currentUser = data.user;
            }).error(function(data, status){
                alert("Error bos : "+status);
                console.log(data);
                $scope.staffOutput = data;
            });
   };
});