import { NgModule } from '@angular/core';
import { AccordionModule } from 'primeng/accordion';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { TooltipModule } from 'primeng/tooltip';

import { MessageModule } from 'primeng/message';
import { TableModule } from 'primeng/table';
import { InputMaskModule } from 'primeng/inputmask';
import { ToastModule } from 'primeng/toast';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import {PasswordModule} from 'primeng/password';

import {InputNumberModule} from 'primeng/inputnumber';
import {DialogModule} from 'primeng/dialog';
import {DividerModule} from 'primeng/divider';


@NgModule({

  imports: [
    AccordionModule,
    InputTextModule,
    ButtonModule,
    TooltipModule,

    MessageModule,
    TableModule,
    InputMaskModule,
    ToastModule,
    ConfirmDialogModule,
    PasswordModule,
    InputNumberModule,
    DialogModule,
    DividerModule

  ],
  exports: [
    AccordionModule,
    InputTextModule,
    ButtonModule,
    TooltipModule,
    MessageModule,
    TableModule,
    InputMaskModule,
    ToastModule,
    ConfirmDialogModule,
    PasswordModule,

    InputNumberModule,
    DialogModule,
    DividerModule
  ],
  providers: []

})
export class PrimeNgModule { }
