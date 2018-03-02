(function() {
    'use strict';

    angular
        .module('eBorgesApp')
        .controller('GeneroDetailController', GeneroDetailController);

    GeneroDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Genero'];

    function GeneroDetailController($scope, $rootScope, $stateParams, previousState, entity, Genero) {
        var vm = this;

        vm.genero = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('eBorgesApp:generoUpdate', function(event, result) {
            vm.genero = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
