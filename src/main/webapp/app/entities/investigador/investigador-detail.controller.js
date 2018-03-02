(function() {
    'use strict';

    angular
        .module('eBorgesApp')
        .controller('InvestigadorDetailController', InvestigadorDetailController);

    InvestigadorDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Investigador', 'Genero', 'Grado', 'Departamento'];

    function InvestigadorDetailController($scope, $rootScope, $stateParams, previousState, entity, Investigador, Genero, Grado, Departamento) {
        var vm = this;

        vm.investigador = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('eBorgesApp:investigadorUpdate', function(event, result) {
            vm.investigador = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
