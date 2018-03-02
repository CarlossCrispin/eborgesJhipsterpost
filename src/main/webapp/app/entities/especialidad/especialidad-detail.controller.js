(function() {
    'use strict';

    angular
        .module('eBorgesApp')
        .controller('EspecialidadDetailController', EspecialidadDetailController);

    EspecialidadDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Especialidad'];

    function EspecialidadDetailController($scope, $rootScope, $stateParams, previousState, entity, Especialidad) {
        var vm = this;

        vm.especialidad = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('eBorgesApp:especialidadUpdate', function(event, result) {
            vm.especialidad = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
