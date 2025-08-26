// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "TestingSupport",
    platforms: [.iOS(.v16)],
    products: [
        .library(
            name: "TestingSupport",
            targets: ["TestingSupport"]),
    ],
    dependencies: [
        .package(path: "../Domain"),
        .package(path: "../CoreCommon")
    ],
    targets: [
        .target(
            name: "TestingSupport",
            dependencies: ["Domain", "CoreCommon"]),
    ]
)
