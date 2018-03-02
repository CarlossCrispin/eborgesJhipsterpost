(function() {
    'use strict';

    angular
        .module('eBorgesApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('unidad', {
            parent: 'entity',
            url: '/unidad',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'eBorgesApp.unidad.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/unidad/unidads.html',
                    controller: 'UnidadController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('unidad');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('unidad-detail', {
            parent: 'unidad',
            url: '/unidad/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'eBorgesApp.unidad.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/unidad/unidad-detail.html',
                    controller: 'UnidadDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('unidad');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Unidad', function($stateParams, Unidad) {
                    return Unidad.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'unidad',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('unidad-detail.edit', {
            parent: 'unidad-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/unidad/unidad-dialog.html',
                    controller: 'UnidadDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Unidad', function(Unidad) {
                            return Unidad.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('unidad.new', {
            parent: 'unidad',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/unidad/unidad-dialog.html',
                    controller: 'UnidadDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nunidad: null,
                                ubicacion: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('unidad', null, { reload: 'unidad' });
                }, function() {
                    $state.go('unidad');
                });
            }]
        })
        .state('unidad.edit', {
            parent: 'unidad',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/unidad/unidad-dialog.html',
                    controller: 'UnidadDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Unidad', function(Unidad) {
                            return Unidad.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('unidad', null, { reload: 'unidad' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('unidad.delete', {
            parent: 'unidad',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/unidad/unidad-delete-dialog.html',
                    controller: 'UnidadDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Unidad', function(Unidad) {
                            return Unidad.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('unidad', null, { reload: 'unidad' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
