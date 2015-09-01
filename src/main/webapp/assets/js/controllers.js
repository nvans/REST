var app = angular.module("UserManagementApp", []);

app.controller("UsersCtrl", function($scope, $http) {
    /**
     * JSON processing
     */
    $http.get('rest/users').
        success(function(data) {
            $scope.users = data;
        });

    /**
     * Visibility of element
     *
     * @type {boolean}
     */
    $scope.isExists = function(obj) {
        for(var i in obj) {
            return obj.hasOwnProperty(i) ? true : false;
        }
    }


});