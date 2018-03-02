(function() {
    'use strict';

    angular
        .module('eBorgesApp')
        .controller('UnidadController', UnidadController);

    UnidadController.$inject = ['Unidad', 'UnidadSearch'];

    function UnidadController(Unidad, UnidadSearch) {

        var vm = this;

        vm.unidads = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Unidad.query(function(result) {
                vm.unidads = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            UnidadSearch.query({query: vm.searchQuery}, function(result) {
                vm.unidads = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
