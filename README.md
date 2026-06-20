# KMP Engine

Game engine 2D minimalista em Kotlin Multiplatform. Projeto de estudo.

Inspirada no **Godot** (scene graph de nós), mas com arquitetura flexível, aberta a outros padrões.
O núcleo é **backend-agnostic**: desenha contra um `Renderer` abstrato — com Skiko como implementação padrão.

> Status: WIP / educacional. Só JVM por enquanto.

## Conceitos

- **Scene graph** — `Node` / `Node2D` em árvore.
- **Delta time** — `FrameClock` para movimento independente de FPS.
- **Renderer abstrato** — backend trocável (Skiko padrão).
- **DSL** — monta cenas com `launch { add<...> { } }`.
- **BoundsOverlay** — debug plugável; desenha os bounds dos nós sobre a cena.

## Exemplo

```kotlin
fun main() = SkikoLauncher().launch(title = "bouncing-ball") {
    add<Ball> {
        radius = 32f
        color = Color.RED
    }
    add<BoundsOverlay> {
        color = Color.BLUE
    }
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
| `runtime-skiko` | Implementação do `Renderer` com Skiko (Skia + Swing). |
| `example/hello-world` | Exemplo básico. |
| `example/bouncing-ball` | Exemplo com física + delta time. |
| `example/colliding-balls` | Exemplo de colisão elástica entre bolas. |
| `example/keyboard-input` | Exemplo de input de teclado (`onInput`). |
| `example/pong` | Pong de dois jogadores (polling via `tree.input`). |
```
