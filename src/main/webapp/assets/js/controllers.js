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
    $scope.isInvisible = false;
    $scope.toggle = function() {
        $scope.isInvisible = !$scope.isInvisible;
    }
});