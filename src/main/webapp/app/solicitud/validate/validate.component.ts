import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ActivatedRoute } from '@angular/router';

import { ValidateService } from './validate.service';

@Component({
    selector: 'jhi-validate',
    templateUrl: './validate.component.html'
})
export class ValidateComponent implements OnInit {
    error: string;
    success: string;

    constructor(private validateService: ValidateService, private route: ActivatedRoute, private router: Router) {}

    ngOnInit() {
        this.route.queryParams.subscribe(params => {
            this.validateService.get(params['codigoReserva']).subscribe(
                () => {
                    this.error = null;
                    this.success = 'OK';
                },
                () => {
                    this.success = null;
                    this.error = 'ERROR';
                }
            );
        });
    }
}
