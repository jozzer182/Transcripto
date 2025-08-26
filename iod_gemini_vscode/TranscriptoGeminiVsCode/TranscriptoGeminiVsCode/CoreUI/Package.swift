// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CoreUI",
    platforms: [.iOS(.v16)],
    products: [
        .library(
            name: "CoreUI",
            targets: ["CoreUI"]),
    ],
    dependencies: [
        .package(path: "../CoreCommon")
    ],
    targets: [
        .target(
            name: "CoreUI",
            dependencies: ["CoreCommon"]),
        .testTarget(
            name: "CoreUITests",
            dependencies: ["CoreUI"]),
    ]
)
