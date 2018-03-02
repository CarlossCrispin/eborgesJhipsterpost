(function() {
    'use strict';

    angular
        .module('eBorgesApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tesis-director', {
            parent: 'entity',
            url: '/tesis-director',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'eBorgesApp.tesisDirector.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tesis-director/tesis-directors.html',
                    controller: 'TesisDirectorController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tesisDirector');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tesis-director-detail', {
            parent: 'tesis-director',
            url: '/tesis-director/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'eBorgesApp.tesisDirector.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tesis-director/tesis-director-detail.html',
                    controller: 'TesisDirectorDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tesisDirector');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'TesisDirector', function($stateParams, TesisDirector) {
                    return TesisDirector.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tesis-director',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tesis-director-detail.edit', {
            parent: 'tesis-director-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tesis-director/tesis-director-dialog.html',
                    controller: 'TesisDirectorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TesisDirector', function(TesisDirector) {
                            return TesisDirector.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tesis-director.new', {
            parent: 'tesis-director',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tesis-director/tesis-director-dialog.html',
                    controller: 'TesisDirectorDialogController',
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
                    $state.go('tesis-director', null, { reload: 'tesis-director' });
                }, function() {
                    $state.go('tesis-director');
                });
            }]
        })
        .state('tesis-director.edit', {
            parent: 'tesis-director',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tesis-director/tesis-director-dialog.html',
                    controller: 'TesisDirectorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TesisDirector', function(TesisDirector) {
                            return TesisDirector.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tesis-director', null, { reload: 'tesis-director' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tesis-director.delete', {
            parent: 'tesis-director',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tesis-director/tesis-director-delete-dialog.html',
                    controller: 'TesisDirectorDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TesisDirector', function(TesisDirector) {
                            return TesisDirector.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tesis-director', null, { reload: 'tesis-director' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
