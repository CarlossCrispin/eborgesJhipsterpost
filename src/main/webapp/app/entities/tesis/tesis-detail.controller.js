(function() {
    'use strict';

    angular
        .module('eBorgesApp')
        .controller('TesisDetailController', TesisDetailController);

    TesisDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Tesis', 'Alumno', 'Grado', 'Departamento', 'Unidad'];

    function TesisDetailController($scope, $rootScope, $stateParams, previousState, entity, Tesis, Alumno, Grado, Departamento, Unidad) {
        var vm = this;

        vm.tesis = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('eBorgesApp:tesisUpdate', function(event, result) {
            vm.tesis = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
