(function() {
    'use strict';

    angular
        .module('eBorgesApp')
        .controller('ActaDetailController', ActaDetailController);

    ActaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Acta', 'Tesis', 'Alumno', 'Unidad'];

    function ActaDetailController($scope, $rootScope, $stateParams, previousState, entity, Acta, Tesis, Alumno, Unidad) {
        var vm = this;

        vm.acta = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('eBorgesApp:actaUpdate', function(event, result) {
            vm.acta = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
