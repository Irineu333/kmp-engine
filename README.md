# KMP 2D Engine

Uma 2D game engine minimalista, backend-agnostic, em Kotlin Multiplatform, para fins de estudo, inspirada no **Godot** (scene graph de nós), mas com arquitetura flexível e aberta a outros padrões.

## Plataformas

O núcleo (`core`, `core-dsl`, `core-debug`) e o `runtime-skiko` compilam para JVM e
wasmJs. Cada plataforma tem sua camada de janela/input:

| Plataforma | Alvo Kotlin | Janela / Input | Exemplos |
|---|---|---|---|
| Desktop | `jvm` | Swing/AWT (`SkikoWindow`) | ✅ todos |
| Navegador | `wasmJs` | `<canvas>` HTML + eventos DOM (`SkikoCanvas`) | ✅ `pong` |

> Só o `pong` está conectado ao navegador por enquanto; os demais exemplos são JVM-only.

## Conceitos

- **Scene graph** — a cena é uma árvore de nós (`Node` / `Node2D`) percorrida a cada frame.
- **Delta time** — a simulação avança pelo tempo entre frames, não pelo FPS.
- **Backend-agnostic** — o `core` desenha contra um `Renderer` abstrato, com backend trocável (Skiko padrão).
- **DSL** — descreve a árvore da cena de forma declarativa, separando o conteúdo da mecânica de montagem.
- **Multi-cena** — cenas nomeadas, reconstruídas a cada troca, alternadas em runtime.
- **Debug plugável** — inspeção opcional (FPS, bounds) num módulo isolado, invisível ao `core`.

## Exemplo

Uma cena com um `Label` embutido e o overlay de debug habilitado.

```kotlin
fun main() = runSkikoWindow(title = "hello-world") {
    scene("main") {
        add(::Label) {
            text = "Hello, World!"
        }
        debug() // overlay de debug (FPS, bounds) — opt-in
    }
}
```

> `add(::Label)` compila em qualquer alvo; no JVM há ainda `add<Label> { }` por reflexão.

Os demais exemplos exercitam funcionalidades específicas da engine:

| Exemplo | Funcionalidade |
|---|---|
| `hello-world` | cena mínima, `Label`, posicionamento |
| `bouncing-ball` | física + delta time, debug com feature própria |
| `colliding-balls` | colisão elástica, montagem da cena com a API pura do `core` (sem DSL) |
| `keyboard-input` | input de teclado (`onInput`) |
| `pong` | jogo completo (2 jogadores): menu, troca de cena, JVM + navegador |

## Requisitos

- **JDK 21**
- **IntelliJ IDEA** (recomendada — o projeto é Gradle/Kotlin Multiplatform)

## Como rodar

```bash
./gradlew :example:hello-world:runJvm
./gradlew :example:bouncing-ball:runJvm
./gradlew :example:colliding-balls:runJvm
./gradlew :example:keyboard-input:runJvm
./gradlew :example:pong:runJvm                       # desktop (JVM)
./gradlew :example:pong:wasmJsBrowserDevelopmentRun  # navegador (wasmJs)
```

## Módulos

| Módulo | Papel |
|---|---|
| `core` | O coração da engine: o `Engine` (orquestrador do loop), uma `SceneTree` única e persistente, o `Game` (representação intermediária das cenas, agnóstica de plataforma), ciclo de vida dos nós, troca de cena e tipos base. Não conhece backend nem plataforma. |
| `core-dsl` | Camada de conveniência para montar cenas de forma declarativa, sobre a API do `core`. |
| `core-debug` | Ferramentas de inspeção opcionais, sem acoplamento ao `core` — pode ser ignorado por completo. |
| `runtime-skiko` | A ponte com o mundo real: implementa o renderer e a janela/input para cada plataforma. |
| `example/*` | Jogos de demonstração que exercitam a engine (ver [Exemplo](#exemplo)). |
