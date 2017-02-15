'use strict';

angular.module("oleSolrClient.report", ['ui.bootstrap', 'ngStorage', 'ui.bootstrap.datetimepicker'])
    .controller('reportCtrl', ["$scope", "$location", "$routeParams", "oleSolrClientAPIService", "$localStorage", '$filter',
        function ($scope, $location, $routeParams, oleSolrClientAPIService, $localStorage, $filter) {
            console.log("Generate Report");

            $scope.generateReport = function () {
                oleSolrClientAPIService.postRestCall(OLESOLRCLIENT_CONSTANTS.GENERATE_REPORT, {}, $scope.reportRequest).then(function (response) {
                    var data = response.data;
                    console.log(data);
                }, function (response) {
                    console.log("Report generation failed");
                    console.log(response);
                    $scope.message = "Submit failed!";
                    $scope.title = "Error";
                    $scope.showDialog = true;
                });
            };
        }]);