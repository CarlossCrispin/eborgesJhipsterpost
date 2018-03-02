(function() {
    'use strict';

    angular
        .module('eBorgesApp')
        .controller('DepartamentoDetailController', DepartamentoDetailController);

    DepartamentoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Departamento', 'Especialidad'];

    function DepartamentoDetailController($scope, $rootScope, $stateParams, previousState, entity, Departamento, Especialidad) {
        var vm = this;

        vm.departamento = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('eBorgesApp:departamentoUpdate', function(event, result) {
            vm.departamento = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
