// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CoreCommon",
    platforms: [.iOS(.v16)],
    products: [
        .library(
            name: "CoreCommon",
            targets: ["CoreCommon"]),
    ],
    targets: [
        .target(
            name: "CoreCommon",
            dependencies: []),
        .testTarget(
            name: "CoreCommonTests",
            dependencies: ["CoreCommon"]),
    ]
)
