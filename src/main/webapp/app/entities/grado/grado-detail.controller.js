(function() {
    'use strict';

    angular
        .module('eBorgesApp')
        .controller('GradoDetailController', GradoDetailController);

    GradoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Grado'];

    function GradoDetailController($scope, $rootScope, $stateParams, previousState, entity, Grado) {
        var vm = this;

        vm.grado = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('eBorgesApp:gradoUpdate', function(event, result) {
            vm.grado = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
