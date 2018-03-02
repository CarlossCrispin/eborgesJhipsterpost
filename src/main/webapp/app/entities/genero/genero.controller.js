(function() {
    'use strict';

    angular
        .module('eBorgesApp')
        .controller('GeneroController', GeneroController);

    GeneroController.$inject = ['Genero', 'GeneroSearch'];

    function GeneroController(Genero, GeneroSearch) {

        var vm = this;

        vm.generos = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Genero.query(function(result) {
                vm.generos = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            GeneroSearch.query({query: vm.searchQuery}, function(result) {
                vm.generos = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
