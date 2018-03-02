(function() {
    'use strict';

    angular
        .module('eBorgesApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tesis', {
            parent: 'entity',
            url: '/tesis',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'eBorgesApp.tesis.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tesis/teses.html',
                    controller: 'TesisController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tesis');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tesis-detail', {
            parent: 'tesis',
            url: '/tesis/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'eBorgesApp.tesis.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tesis/tesis-detail.html',
                    controller: 'TesisDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tesis');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Tesis', function($stateParams, Tesis) {
                    return Tesis.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tesis',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tesis-detail.edit', {
            parent: 'tesis-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tesis/tesis-dialog.html',
                    controller: 'TesisDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tesis', function(Tesis) {
                            return Tesis.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tesis.new', {
            parent: 'tesis',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tesis/tesis-dialog.html',
                    controller: 'TesisDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                titulodetesis: null,
                                fechadepublicacion: null,
                                resumen: null,
                                clasificacion: null,
                                clasificacion1: null,
                                anio: null,
                                mes: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('tesis', null, { reload: 'tesis' });
                }, function() {
                    $state.go('tesis');
                });
            }]
        })
        .state('tesis.edit', {
            parent: 'tesis',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tesis/tesis-dialog.html',
                    controller: 'TesisDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tesis', function(Tesis) {
                            return Tesis.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tesis', null, { reload: 'tesis' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tesis.delete', {
            parent: 'tesis',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tesis/tesis-delete-dialog.html',
                    controller: 'TesisDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Tesis', function(Tesis) {
                            return Tesis.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tesis', null, { reload: 'tesis' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
