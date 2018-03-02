(function() {
    'use strict';

    angular
        .module('eBorgesApp')
        .controller('TesisDirectorController', TesisDirectorController);

    TesisDirectorController.$inject = ['TesisDirector', 'TesisDirectorSearch'];

    function TesisDirectorController(TesisDirector, TesisDirectorSearch) {

        var vm = this;

        vm.tesisDirectors = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            TesisDirector.query(function(result) {
                vm.tesisDirectors = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            TesisDirectorSearch.query({query: vm.searchQuery}, function(result) {
                vm.tesisDirectors = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
