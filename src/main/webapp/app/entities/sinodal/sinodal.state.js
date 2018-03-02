(function() {
    'use strict';

    angular
        .module('eBorgesApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('sinodal', {
            parent: 'entity',
            url: '/sinodal',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'eBorgesApp.sinodal.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sinodal/sinodals.html',
                    controller: 'SinodalController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('sinodal');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('sinodal-detail', {
            parent: 'sinodal',
            url: '/sinodal/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'eBorgesApp.sinodal.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sinodal/sinodal-detail.html',
                    controller: 'SinodalDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('sinodal');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Sinodal', function($stateParams, Sinodal) {
                    return Sinodal.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'sinodal',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('sinodal-detail.edit', {
            parent: 'sinodal-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sinodal/sinodal-dialog.html',
                    controller: 'SinodalDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Sinodal', function(Sinodal) {
                            return Sinodal.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('sinodal.new', {
            parent: 'sinodal',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sinodal/sinodal-dialog.html',
                    controller: 'SinodalDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('sinodal', null, { reload: 'sinodal' });
                }, function() {
                    $state.go('sinodal');
                });
            }]
        })
        .state('sinodal.edit', {
            parent: 'sinodal',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sinodal/sinodal-dialog.html',
                    controller: 'SinodalDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Sinodal', function(Sinodal) {
                            return Sinodal.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('sinodal', null, { reload: 'sinodal' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('sinodal.delete', {
            parent: 'sinodal',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sinodal/sinodal-delete-dialog.html',
                    controller: 'SinodalDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Sinodal', function(Sinodal) {
                            return Sinodal.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('sinodal', null, { reload: 'sinodal' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
