import { Component, signal } from '@angular/core';
import { RouterOutlet, RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, RouterLink, RouterLinkActive],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  annee = new Date().getFullYear();
  ouvert = signal(false);

  basculer(): void { this.ouvert.update(v => !v); }
  fermer(): void { this.ouvert.set(false); }
}