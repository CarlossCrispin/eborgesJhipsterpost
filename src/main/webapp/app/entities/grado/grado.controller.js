(function() {
    'use strict';

    angular
        .module('eBorgesApp')
        .controller('GradoController', GradoController);

    GradoController.$inject = ['Grado', 'GradoSearch'];

    function GradoController(Grado, GradoSearch) {

        var vm = this;

        vm.grados = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Grado.query(function(result) {
                vm.grados = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            GradoSearch.query({query: vm.searchQuery}, function(result) {
                vm.grados = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
