(function() {
    'use strict';

    angular
        .module('eBorgesApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tipo-asesor', {
            parent: 'entity',
            url: '/tipo-asesor',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'eBorgesApp.tipoAsesor.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tipo-asesor/tipo-asesors.html',
                    controller: 'TipoAsesorController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tipoAsesor');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tipo-asesor-detail', {
            parent: 'tipo-asesor',
            url: '/tipo-asesor/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'eBorgesApp.tipoAsesor.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tipo-asesor/tipo-asesor-detail.html',
                    controller: 'TipoAsesorDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tipoAsesor');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'TipoAsesor', function($stateParams, TipoAsesor) {
                    return TipoAsesor.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tipo-asesor',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tipo-asesor-detail.edit', {
            parent: 'tipo-asesor-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tipo-asesor/tipo-asesor-dialog.html',
                    controller: 'TipoAsesorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TipoAsesor', function(TipoAsesor) {
                            return TipoAsesor.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tipo-asesor.new', {
            parent: 'tipo-asesor',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tipo-asesor/tipo-asesor-dialog.html',
                    controller: 'TipoAsesorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                ntipo: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('tipo-asesor', null, { reload: 'tipo-asesor' });
                }, function() {
                    $state.go('tipo-asesor');
                });
            }]
        })
        .state('tipo-asesor.edit', {
            parent: 'tipo-asesor',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tipo-asesor/tipo-asesor-dialog.html',
                    controller: 'TipoAsesorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TipoAsesor', function(TipoAsesor) {
                            return TipoAsesor.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tipo-asesor', null, { reload: 'tipo-asesor' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tipo-asesor.delete', {
            parent: 'tipo-asesor',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tipo-asesor/tipo-asesor-delete-dialog.html',
                    controller: 'TipoAsesorDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TipoAsesor', function(TipoAsesor) {
                            return TipoAsesor.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tipo-asesor', null, { reload: 'tipo-asesor' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
