'use strict';

angular.module("oleSolrClient.partialIndex", ['ui.bootstrap', 'ngStorage', 'ui.bootstrap.datetimepicker'])
    .controller('partialIndexCtrl', ["$scope", "$location", "$routeParams", "oleSolrClientAPIService", "$localStorage", '$filter',
        function ($scope, $location, $routeParams, oleSolrClientAPIService, $localStorage, $filter) {
            console.log("Partial Index");
        }]);