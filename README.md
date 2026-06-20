# KMP Engine

Game engine 2D minimalista em Kotlin Multiplatform. Projeto de estudo.

Inspirada no **Godot** (scene graph de nós), mas com arquitetura flexível, aberta a outros padrões.
O núcleo é **backend-agnostic**: desenha contra um `Renderer` abstrato — com Skiko como implementação padrão.

> Status: WIP / educacional. Só JVM por enquanto.

## Conceitos

- **Scene graph** — `Node` / `Node2D` em árvore.
- **Delta time** — `FrameClock` para movimento independente de FPS.
- **Renderer abstrato** — backend trocável (Skiko padrão).
- **DSL** — monta cenas nomeadas com `runSkikoWindow { scene(...) { add<...> { } } }` (a primeira cena registrada é a inicial).
- **Multi-cena** — registre várias cenas e troque em runtime com `tree.changeScene("nome")`.
- **BoundsOverlay** — debug plugável; desenha os bounds dos nós sobre a cena.

## Exemplo

```kotlin
fun main() = runSkikoWindow(title = "bouncing-ball") {
    scene("main") {
        add<Ball> {
            radius = 32f
            color = Color.RED
        }
        add<BoundsOverlay> {
            color = Color.BLUE
        }
    }
}
```

Várias cenas e troca em runtime (a primeira cena é a inicial; ex.: menu → jogo):

```kotlin
fun main() = runSkikoWindow(title = "pong") {
    scene("menu") { add<MenuController>() /* ENTER → changeScene("pong") */ }
    scene("pong") { add<ReturnToMenu>()   /* ESC   → changeScene("menu") */ }
}
```

## Requisitos

JDK 21. Kotlin e Gradle via wrapper.

## Como rodar

```bash
./gradlew :example:hello-world:run
./gradlew :example:bouncing-ball:run
./gradlew :example:colliding-balls:run
./gradlew :example:keyboard-input:run
./gradlew :example:pong:run
```

## Módulos

| Módulo | Papel |
|---|---|
| `core` | Nodes, scene tree, `Renderer` abstrato, frame clock, tipos (`Vec2`, `Color`, `Rect`, `Size`). |
| `core-dsl` | DSL para construir cenas. |
| `runtime-skiko` | Implementação do `Renderer` e da janela (`SkikoWindow`) com Skiko (Skia + Swing). |
| `example/hello-world` | Exemplo básico. |
| `example/bouncing-ball` | Exemplo com física + delta time. |
| `example/colliding-balls` | Colisão elástica entre bolas; monta a cena com a API pura do `core` (sem DSL). |
| `example/keyboard-input` | Exemplo de input de teclado (`onInput`). |
| `example/pong` | Pong de dois jogadores com menu inicial e troca de cena (`tree.changeScene`). |
```
