(function() {
    'use strict';

    angular
        .module('eBorgesApp')
        .controller('AlumnoDetailController', AlumnoDetailController);

    AlumnoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Alumno', 'Departamento', 'Grado', 'Genero'];

    function AlumnoDetailController($scope, $rootScope, $stateParams, previousState, entity, Alumno, Departamento, Grado, Genero) {
        var vm = this;

        vm.alumno = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('eBorgesApp:alumnoUpdate', function(event, result) {
            vm.alumno = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
