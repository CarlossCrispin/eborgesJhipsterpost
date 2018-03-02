(function() {
    'use strict';

    angular
        .module('eBorgesApp')
        .controller('SinodalController', SinodalController);

    SinodalController.$inject = ['Sinodal', 'SinodalSearch'];

    function SinodalController(Sinodal, SinodalSearch) {

        var vm = this;

        vm.sinodals = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Sinodal.query(function(result) {
                vm.sinodals = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            SinodalSearch.query({query: vm.searchQuery}, function(result) {
                vm.sinodals = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
