![arkkitehtuuri](arkkitehtuuri.svg)

<!--

[Application]<-[Ui||start(window: Stage); static run()]1--[Map||index(x: int, y: int): double; getWidth(): int; getHeight(): int; makePerlin(scale: double, influence: double, offset: double); waterCutoff(cutoff: double); doErosion(drops: int, iterations: int); toBufferedImage(): BufferedImage]

[Task]<-[MapTask||call(): Map]1--[Map]
[Ui]1--0..1[MapTask]

-->
