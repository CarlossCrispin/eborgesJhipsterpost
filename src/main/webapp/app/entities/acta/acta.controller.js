(function() {
    'use strict';

    angular
        .module('eBorgesApp')
        .controller('ActaController', ActaController);

    ActaController.$inject = ['Acta', 'ActaSearch'];

    function ActaController(Acta, ActaSearch) {

        var vm = this;

        vm.actas = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Acta.query(function(result) {
                vm.actas = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            ActaSearch.query({query: vm.searchQuery}, function(result) {
                vm.actas = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
