(function() {
    'use strict';

    angular
        .module('eBorgesApp')
        .controller('UnidadDetailController', UnidadDetailController);

    UnidadDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Unidad'];

    function UnidadDetailController($scope, $rootScope, $stateParams, previousState, entity, Unidad) {
        var vm = this;

        vm.unidad = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('eBorgesApp:unidadUpdate', function(event, result) {
            vm.unidad = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
